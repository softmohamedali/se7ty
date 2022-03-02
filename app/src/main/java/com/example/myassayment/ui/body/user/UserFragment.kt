package com.example.myassayment.ui.body.user

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
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
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class UserFragment : Fragment() {
    private var _binding: FragmentUserBinding?=null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()
    private lateinit var userInfo:Client
    private lateinit var photourl:String
    private lateinit var email:String
    private lateinit var name:String
    private var imgUser:ByteArray?=null
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
        binding.etNameUserfrag.setText(name)
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
        binding.imgUser.load(userInfo.photoUrl)
        binding.btnBackUserfrag.setOnClickListener { findNavController().popBackStack() }
        binding.btnPickpositionUserfrag.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_pickPositionFragment)
        }
        if (!navArgs.lang?.trim().isNullOrEmpty()&&!navArgs.lati?.trim().isNullOrEmpty())
        {
            binding.etPositionUserfrag.setText("${navArgs.lati},${navArgs.lang}")
        }
        binding.imgUser.setOnClickListener {
            selectImg()
        }
    }

    private fun saveUser() {
        val emailToContact=binding.etEmailUserfrag.text.toString()
        val name=binding.etNameUserfrag.text.toString()
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
            ),imgUser)
        }else{
            return
        }

    }
    private fun selectImg() {
        val intent= Intent().apply {
            action= Intent.ACTION_GET_CONTENT
            type="image/*"
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/png","image/jpeg"))
        }
        registerLaunche.launch(intent)
    }
    val registerLaunche=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.data!=null&&it.resultCode== Activity.RESULT_OK){
            val imgUrl=it.data!!.data
            val bitmap= MediaStore.Images.Media.getBitmap(requireActivity().contentResolver,imgUrl)
            val outByteArray= ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG,25,outByteArray)
            val outByteArra=outByteArray.toByteArray()
            imgUser=outByteArra
            binding.imgUser.setImageURI(imgUrl)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}