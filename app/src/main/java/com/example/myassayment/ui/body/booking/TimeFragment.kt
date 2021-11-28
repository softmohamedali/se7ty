package com.example.myassayment.ui.body.booking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myassayment.R
import com.example.myassayment.databinding.FragmentDateBinding
import com.example.myassayment.databinding.FragmentTimeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimeFragment : Fragment() {
    private var _binding: FragmentTimeBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentTimeBinding.inflate(layoutInflater)
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