package com.example.myassayment.ui.body.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myassayment.R
import com.example.myassayment.databinding.FragmentMyElecticalRecordBinding
import com.example.myassayment.databinding.FragmentPersonalInfoBinding
import com.example.myassayment.models.Client
import com.example.myassayment.utils.StatusResult
import com.example.myassayment.viewmodels.AuthViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonalInfoFragment : Fragment() {
    private var _binding: FragmentPersonalInfoBinding?=null
    private val binding get() = _binding!!
    private val viewModel by viewModels<AuthViewModel>()
    private lateinit var userInfo:Client
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentPersonalInfoBinding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    private fun setUp() {
        viewModel.getUserInfo()
        setUpObservers()
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnSave.setOnClickListener {
            saveUserinfo()
        }
        binding.btnClose.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun saveUserinfo() {
        val heightUser=binding.etHeight.text.toString()
        val weightUser=binding.etWeight.text.toString()
        val mStatusUser=binding.etMaterial.text.toString()
        val blodeUser=binding.etBlode.text.toString()
        val smokerUser=binding.swSmoker.isChecked.toString()
        val elcholeUser=binding.swElchole.isChecked.toString()
        val client=userInfo
        val cleintInfo=client.info
        cleintInfo?.materialStatus=mStatusUser
        cleintInfo?.weight=weightUser
        cleintInfo?.height=heightUser
        cleintInfo?.blodeType=blodeUser
        cleintInfo?.elchole=elcholeUser
        cleintInfo?.smoker=smokerUser
        client.info=cleintInfo
        viewModel.saveUser(client,null)
    }

    private fun setUpObservers() {
        viewModel.isgetUser.observe(viewLifecycleOwner,{
            when{
                it is StatusResult.OnLoading ->{
                    showLoading(true)
                }
                it is StatusResult.OnError-> {
                    showLoading(false)
                    Toast.makeText(requireActivity(),it.msg, Toast.LENGTH_SHORT).show()
                }
                it is StatusResult.OnSuccess ->{
                    showLoading(false)
                    userInfo=it.data!!
                    setUpView()
                }
            }
        })
        viewModel.isSave.observe(viewLifecycleOwner,{
            when{
                it is StatusResult.OnLoading ->{
                    showLoading(true)
                }
                it is StatusResult.OnError-> {
                    showLoading(false)
                    Toast.makeText(requireActivity(),it.msg, Toast.LENGTH_SHORT).show()
                }
                it is StatusResult.OnSuccess ->{
                    showLoading(false)
                    Snackbar.make(binding.root,"save success", Snackbar.LENGTH_SHORT)
                        .setAction("ok",{}).show()
                }
            }
        })
    }

    private fun setUpView() {
        binding.etBlode.setText(userInfo.info?.blodeType)
        binding.etHeight.setText(userInfo.info?.height)
        binding.etWeight.setText(userInfo.info?.weight)
        binding.etMaterial.setText(userInfo.info?.materialStatus)
        binding.swElchole.isChecked=userInfo.info?.elchole.toBoolean()
        binding.swSmoker.isChecked=userInfo.info?.smoker.toBoolean()
    }

    private fun showLoading(show:Boolean)
    {
        if (show){
            binding.pbPersonal.isVisible=true
            binding.viewPersonal.setBackgroundColor(ContextCompat
                .getColor(requireContext(),R.color.blackop))
            binding.containerPersonal.isActivated=false
        }else{
            binding.pbPersonal.isVisible=false
            binding.viewPersonal.setBackgroundColor(ContextCompat
                .getColor(requireContext(),R.color.none))
            binding.containerPersonal.isActivated=true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}