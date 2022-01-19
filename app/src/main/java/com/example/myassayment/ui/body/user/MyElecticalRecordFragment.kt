package com.example.myassayment.ui.body.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myassayment.R
import com.example.myassayment.databinding.FragmentAccountBinding
import com.example.myassayment.databinding.FragmentMyElecticalRecordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyElecticalRecordFragment : Fragment() {
    private var _binding: FragmentMyElecticalRecordBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentMyElecticalRecordBinding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    private fun setUp() {
        binding.imgBackMyelec.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnMedical.setOnClickListener {
            findNavController().navigate(R.id.action_myElecticalRecordFragment_to_medicalTestFragment)
        }
        binding.btnMedication.setOnClickListener {
            findNavController().navigate(R.id.action_myElecticalRecordFragment_to_medicationFragment)
        }
        binding.btnPerInfo.setOnClickListener {
            findNavController().navigate(R.id.action_myElecticalRecordFragment_to_personalInfoFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}