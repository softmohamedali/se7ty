package com.example.myassayment.ui.body.serviesesfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myassayment.R
import com.example.myassayment.databinding.FragmentMainBinding
import com.example.myassayment.databinding.FragmentServicesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServicesFragment : Fragment() {
    private var _binding: FragmentServicesBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentServicesBinding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    private fun setUp() {
        binding.containerLapTesstServices.setOnClickListener {
            findNavController().navigate(R.id.action_servicesFragment_to_lapTestFragment)
        }
        binding.containerCovied19Services.setOnClickListener {
            findNavController().navigate(R.id.action_servicesFragment_to_covid19Fragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}