package com.example.myassayment.ui.body.bottomcheat

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
import com.example.myassayment.databinding.FragmentBottomChatBinding
import com.example.myassayment.databinding.FragmentGetHelpBinding
import com.example.myassayment.helpers.Validation
import com.example.myassayment.models.Massage
import com.example.myassayment.utils.MyUtils
import com.example.myassayment.utils.StatusResult
import com.example.myassayment.viewmodels.BottomCheatViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GetHelpFragment : Fragment() {
    private var _binding: FragmentGetHelpBinding?=null
    private val binding get() = _binding!!
    private val bottomCheatViewModel by viewModels<BottomCheatViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentGetHelpBinding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    private fun setUp() {
        binding.btnBackGethelp.setOnClickListener {
            findNavController().popBackStack(R.id.mainFragment,false)
        }
        binding.btnSendBottomcheat.setOnClickListener {
            if (validateInputs(){})
            {
                validateInputs {
                    bottomCheatViewModel.upLoadMassage(it)
                }
            }
        }
        bottomCheatViewModel.isUploadMassage.observe(viewLifecycleOwner,{
            when{
                it is StatusResult.OnError ->{
                    Toast.makeText(requireContext(), "${it.msg}", Toast.LENGTH_SHORT).show()
                    binding.pbGethelp.isVisible=false
                }
                it is StatusResult.OnLoading -> {
                    binding.pbGethelp.isVisible=true
                }
                it is StatusResult.OnSuccess ->{
                    binding.pbGethelp.isVisible=false
                    MyUtils.toastSuccessBooking(requireActivity(),"Success send Massage")
                }
            }
        })
    }

    private fun validateInputs(massage:(Massage)->Unit):Boolean {
        val name=binding.etNameGethelp.text.toString()
        val email=binding.etEmailGethelp.text.toString()
        val subject=binding.etSubjectGethelp.text.toString()
        val massagee=binding.etMassageGethelp.text.toString()

        if (Validation.isFeildIsEmpty(binding.etNameGethelp)||
            Validation.isNotValidEmail(binding.etEmailGethelp)||
            Validation.isFeildIsEmpty(binding.etSubjectGethelp)||
            Validation.isFeildIsEmpty(binding.etMassageGethelp))
        {
            return false
        }
        val msg=Massage(
            subject = subject,
            name = name,
            email = email,
            msg= massagee
        )
        massage(msg)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}