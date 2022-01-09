package com.example.myassayment.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.myassayment.databinding.FragmentLogInBinding
import com.example.myassayment.helpers.Validation
import com.example.myassayment.models.Client
import com.example.myassayment.utils.Constants
import com.example.myassayment.utils.MyUtils
import com.example.myassayment.utils.StatusResult
import com.example.myassayment.viewmodels.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogInFragment : Fragment() {
    private var _binding: FragmentLogInBinding?=null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentLogInBinding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    private fun setUp() {
        setUpObservers()
        binding.etRegisterLogfrag.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_singUpFragment)
        }
        binding.etForgetPassLogfrag.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_forgetPasswordFragment)
        }
        binding.btnLoginLogfrag.setOnClickListener {
            singIn()
        }
        binding.btnGoogleLogfrag.setOnClickListener {
            startActivityForResult(authViewModel.logIngoogle(),Constants.RC_SING_IN)
        }
    }

    private fun setUpObservers() {
        authViewModel.isLogIn.observe(viewLifecycleOwner,{
            when{
                it is StatusResult.OnError ->{
                    binding.prLogFrag.isVisible=false
                    Toast.makeText(requireContext(), "${it.msg}", Toast.LENGTH_SHORT).show()
                }
                it is StatusResult.OnLoading -> {
                    binding.prLogFrag.isVisible=true
                }
                it is StatusResult.OnSuccess ->{
                    binding.prLogFrag.isVisible=false
                    findNavController().popBackStack()
                }
            }
        })

        authViewModel.isRegister.observe(viewLifecycleOwner,{
            when{
                it is StatusResult.OnError ->{
                    binding.prLogFrag.isVisible=false
                    Toast.makeText(requireContext(), "${it.msg}", Toast.LENGTH_SHORT).show()
                }
                it is StatusResult.OnLoading -> {
                    binding.prLogFrag.isVisible=true
                }
                it is StatusResult.OnSuccess ->{
                    binding.prLogFrag.isVisible=false
                    findNavController().popBackStack()
                }
            }
        })
    }

    private fun singIn() {
        val email=binding.etEmailLogfrag.text.toString()
        val password=binding.etPasswordLogfrag.text.toString()
        if (
            !Validation.isNotValidEmail(binding.etEmailLogfrag)&&
            Validation.isValidPassword(binding.etPasswordLogfrag)
        )
        {
            authViewModel.logIn(email,password)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.RC_SING_IN) {

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            if (task.isSuccessful) {
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    authViewModel.firebaseSinginWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                }
            } else {
                Log.d("mylog",task.exception.toString())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }


}