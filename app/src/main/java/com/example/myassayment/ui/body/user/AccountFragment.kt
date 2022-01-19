package com.example.myassayment.ui.body.user

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import dagger.hilt.android.AndroidEntryPoint
import android.graphics.drawable.ColorDrawable
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.myassayment.R
import com.example.myassayment.databinding.FragmentAccountBinding
import com.example.myassayment.models.Client
import com.example.myassayment.utils.StatusResult
import com.example.myassayment.viewmodels.AuthViewModel
import com.google.firebase.auth.FirebaseUser


@AndroidEntryPoint
class AccountFragment : DialogFragment() {
    private var _binding: FragmentAccountBinding?=null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            dialog.window!!.setLayout(
                (getResources().getDisplayMetrics().widthPixels*0.90).toInt(),
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentAccountBinding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    private fun setUp() {
        authViewModel.getUserInfo()
        setUpObsevers()
        binding.tvLogoutAccount.setOnClickListener {
            authViewModel.logOut()
            findNavController().popBackStack()
        }
        binding.imgLogoutAccount.setOnClickListener {
            authViewModel.logOut()
            findNavController().popBackStack()
        }
        binding.imgFinishAccount.setOnClickListener {
            findNavController().navigate(R.id.action_accountFragment_to_userFragment)
        }
        binding.tvFinichAccount.setOnClickListener {
            findNavController().navigate(R.id.action_accountFragment_to_userFragment)
        }
        binding.tvMyelectricalAccount.setOnClickListener {
            findNavController().navigate(R.id.action_accountFragment_to_myElecticalRecordFragment)
        }
    }

    private fun setUpObsevers() {
        authViewModel.isgetUser.observe(viewLifecycleOwner,{
            when{
                it is StatusResult.OnError ->{
                    Toast.makeText(requireContext(), "${it.msg}", Toast.LENGTH_SHORT).show()
                }
                it is StatusResult.OnLoading -> {
                }
                it is StatusResult.OnSuccess ->{
                    setUserInfo(it.data!!)
                }
            }
        })
    }

    private fun setUserInfo(client: Client) {
            binding.imgUserAccount.load(client.photoUrl)
            binding.tvUserNameAccount.text=client.name
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}