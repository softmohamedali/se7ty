package com.example.myassayment.ui.body.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myassayment.R
import com.example.myassayment.databinding.FragmentMedicalTestBinding
import com.example.myassayment.databinding.FragmentMyElecticalRecordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MedicalTestFragment : Fragment() {
    private var _binding: FragmentMedicalTestBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentMedicalTestBinding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    private fun setUp() {

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}