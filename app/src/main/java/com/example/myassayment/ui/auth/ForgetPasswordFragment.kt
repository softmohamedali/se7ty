package com.example.myassayment.ui.auth

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
import com.example.myassayment.databinding.FragmentForgetPasswordBinding
import com.example.myassayment.databinding.FragmentLogInBinding
import com.example.myassayment.helpers.Validation
import com.example.myassayment.utils.MyUtils
import com.example.myassayment.utils.StatusResult
import com.example.myassayment.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetPasswordFragment : Fragment() {
    private var _binding: FragmentForgetPasswordBinding?=null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentForgetPasswordBinding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    private fun setUp() {
        setUpObservers()
        binding.btnFindpassForget.setOnClickListener {
            val email=binding.etEmailForget.text.toString()
            if (!Validation.isNotValidEmail(binding.etEmailForget))
            {
                authViewModel.findMyPassword(email)
            }
        }

        binding.btnBackForget.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun setUpObservers() {
        authViewModel.isPassSend.observe(viewLifecycleOwner,{
            when{
                it is StatusResult.OnError ->{
                    binding.prLogForget.isVisible=false
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                }
                it is StatusResult.OnLoading -> {
                    binding.prLogForget.isVisible=true
                }
                it is StatusResult.OnSuccess ->{
                    binding.prLogForget.isVisible=false
                    MyUtils.toastSuccessBooking(requireActivity(),"successfuly send Passwoed cheak your Mial")
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}