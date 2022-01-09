package com.example.myassayment.ui.body.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.myassayment.R
import com.example.myassayment.databinding.FragmentAccountBinding
import com.example.myassayment.databinding.FragmentUserBinding
import com.example.myassayment.helpers.Validation
import com.example.myassayment.models.Client
import com.example.myassayment.utils.MyUtils
import com.example.myassayment.utils.StatusResult
import com.example.myassayment.viewmodels.AuthViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment() {
    private var _binding: FragmentUserBinding?=null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()
    private lateinit var userInfo:Client
    private lateinit var photourl:String
    private lateinit var email:String
    private lateinit var name:String
    private val navArgs by navArgs<UserFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentUserBinding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    private fun setUp() {
        authViewModel.getUserInfo()
        setUpObserver()
    }

    private fun setUpObserver() {
        authViewModel.isSave.observe(viewLifecycleOwner,{
            when{
                it is StatusResult.OnLoading ->{
                    binding.progressBar.isVisible=true
                }
                it is StatusResult.OnError-> {
                    binding.progressBar.isVisible=false
                    Toast.makeText(requireActivity(),it.msg, Toast.LENGTH_SHORT).show()
                }
                it is StatusResult.OnSuccess ->{
                    binding.progressBar.isVisible=false
                    Snackbar.make(binding.root,"save success",Snackbar.LENGTH_SHORT)
                        .setAction("ok",{}).show()
                }
            }
        })
        authViewModel.isgetUser.observe(viewLifecycleOwner,{
            when{
                it is StatusResult.OnLoading ->{
                    binding.progressBar.isVisible=true
                }
                it is StatusResult.OnError-> {
                    binding.progressBar.isVisible=false
                    Toast.makeText(requireActivity(),it.msg, Toast.LENGTH_SHORT).show()
                }
                it is StatusResult.OnSuccess ->{
                    binding.progressBar.isVisible=false
                    userInfo=it.data!!
                    setUpView()
                }
            }
        })
    }

    private fun setUpView() {
        photourl=userInfo.photoUrl!!
        email=userInfo.email!!
        name=userInfo.name!!
        binding.tvEmailUserfrag.text="Email: ${email}"
        binding.tvNameUserfrag.text="Name : ${name}"
        binding.etEmailUserfrag.setText(userInfo.emailToContact)
        binding.etPhoneUserfrag.setText(userInfo.phone)
        binding.etGenderUserfrag.setText(userInfo.gender)
        binding.etAgeUserfrag.setText(userInfo.age)
        binding.etDeasesUserfrag.setText(userInfo.desease)
        binding.etPositionUserfrag.setText(userInfo.position)
        val genders= arrayOf("Female","Male")
        val adapterGender=ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,genders)
        binding.etGenderUserfrag.setAdapter(adapterGender)
        binding.btnSavelUserfrag.setOnClickListener {
            saveUser()
        }
        binding.btnBackUserfrag.setOnClickListener { findNavController().popBackStack() }
        binding.btnPickpositionUserfrag.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_pickPositionFragment)
        }
        if (!navArgs.lang?.trim().isNullOrEmpty()&&!navArgs.lati?.trim().isNullOrEmpty())
        {
            binding.etPositionUserfrag.setText("${navArgs.lati},${navArgs.lang}")
        }
    }

    private fun saveUser() {
        val emailToContact=binding.etEmailUserfrag.text.toString()
        val phone=binding.etPhoneUserfrag.text.toString()
        val gender=binding.etGenderUserfrag.text.toString()
        val age=binding.etAgeUserfrag.text.toString()
        val deases=binding.etDeasesUserfrag.text.toString()
        val position=binding.etPositionUserfrag.text.toString()
        if (!Validation.isNotValidEmail(binding.etEmailUserfrag)&&
                Validation.validateMobile(binding.etPhoneUserfrag)&&
               !Validation.isFeildIsEmpty(binding.etAgeUserfrag))
        {
            authViewModel.saveUser(Client(
                email = email,
                name = name,
                photoUrl = photourl,
                phone = phone,
                emailToContact=emailToContact,
                gender = gender,
                age = age,
                desease = deases,
                position = position
            ))
        }else{
            return
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}