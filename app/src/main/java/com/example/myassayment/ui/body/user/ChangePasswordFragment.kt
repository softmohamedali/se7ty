package com.example.myassayment.ui.body.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myassayment.R
import com.example.myassayment.databinding.FragmentChangePasswordBinding
import com.example.myassayment.databinding.FragmentSettingBinding
import com.example.myassayment.helpers.Validation
import com.example.myassayment.utils.StatusResult
import com.example.myassayment.viewmodels.AuthViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment : Fragment() {
    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentChangePasswordBinding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    private fun setUp() {
        setUpObservers()
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnChangePass.setOnClickListener {
            changePass()
        }
    }

    private fun setUpObservers() {
        authViewModel.isChangePass.observe(viewLifecycleOwner,{
            when{
                it is StatusResult.OnSuccess ->{
                    binding.pb.isVisible=false
                    Snackbar.make(binding.root,"password Changed Successfuly",Snackbar.LENGTH_SHORT)
                        .setAction("OK",{}).show()
                }
                it is StatusResult.OnLoading ->{
                    binding.pb.isVisible=true
                }
                it is StatusResult.OnError ->{
                    binding.pb.isVisible=false
                    Toast.makeText(requireContext(), "${it.msg}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun changePass() {
        val currentPass=binding.etCurrentPass.text.toString()
        val newPass=binding.etNewPass.text.toString()

        if (
            Validation.isFeildIsEmpty(binding.etCurrentPass)||
                    Validation.isFeildIsEmpty(binding.etNewPass)
        ){
            return
        }
        authViewModel.changePassword(curretPass = currentPass,newPass = newPass)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}