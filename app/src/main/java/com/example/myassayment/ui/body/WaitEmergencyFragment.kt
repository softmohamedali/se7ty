package com.example.myassayment.ui.body

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myassayment.R
import com.example.myassayment.databinding.FragmentEmergencyBinding
import com.example.myassayment.databinding.FragmentWaitEmergencyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WaitEmergencyFragment : Fragment() {
    private var _binding: FragmentWaitEmergencyBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentWaitEmergencyBinding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    private fun setUp() {
        binding.btnCancleWaitemergency.setOnClickListener {
            findNavController().navigate(R.id.action_waitEmergencyFragment_to_cancleEmrgencyFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}