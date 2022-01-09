package com.example.myassayment.ui.body.serviesesfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myassayment.R
import com.example.myassayment.databinding.FragmentCovid19Binding
import com.example.myassayment.databinding.FragmentLapTestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Covid19Fragment : Fragment() {
    private var _binding: FragmentCovid19Binding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentCovid19Binding.inflate(layoutInflater)
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