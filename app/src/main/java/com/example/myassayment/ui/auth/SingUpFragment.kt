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
import com.example.myassayment.databinding.FragmentBookDateBinding
import com.example.myassayment.databinding.FragmentSingUpBinding
import com.example.myassayment.helpers.Validation
import com.example.myassayment.models.Client
import com.example.myassayment.utils.MyUtils
import com.example.myassayment.utils.StatusResult
import com.example.myassayment.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingUpFragment : Fragment() {
    private var _binding: FragmentSingUpBinding?=null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentSingUpBinding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    private fun setUp() {
        setUpObservers()
        binding.btnBackSingup.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnSingupSingup.setOnClickListener {
            singUP()
        }
    }

    private fun setUpObservers() {
        authViewModel.isRegister.observe(viewLifecycleOwner,{
            when{
                it is StatusResult.OnError ->{
                    binding.prSingup.isVisible=false
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                }
                it is StatusResult.OnLoading -> {
                    binding.prSingup.isVisible=true
                }
                it is StatusResult.OnSuccess ->{
                    binding.prSingup.isVisible=false
                    MyUtils.toastSuccessBooking(requireActivity(),"successfuly Sing Up")
                    findNavController().popBackStack()
                }
            }
        })
    }

    private fun singUP() {
        val email=binding.etEmailSingup.text.toString()
        val password=binding.etPasswordSingup.text.toString()
        val phone=binding.etPhoneSingup.text.toString()
        if (
            !Validation.isNotValidEmail(binding.etEmailSingup)&&
            Validation.validateMobile(binding.etPhoneSingup)&&
                Validation.isValidPassword(binding.etPasswordSingup)&&
                    binding.cbAgreeTermsSingup.isChecked
                )
        {
            authViewModel.register(email,password,Client(phone=phone))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}