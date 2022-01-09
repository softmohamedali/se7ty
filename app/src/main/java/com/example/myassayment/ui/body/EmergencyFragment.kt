package com.example.myassayment.ui.body

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myassayment.R
import com.example.myassayment.databinding.FragmentEmergencyBinding
import com.example.myassayment.databinding.FragmentPreviousBookingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmergencyFragment : Fragment() {
    private var _binding: FragmentEmergencyBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentEmergencyBinding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    private fun setUp() {
        binding.btnGethelpEmergncy.setOnClickListener {
            findNavController().navigate(R.id.action_emergencyFragment_to_getHelpFragment)
        }
        binding.btnEmergencyEmergency.setOnClickListener {
            findNavController().navigate(R.id.action_emergencyFragment_to_waitEmergencyFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}