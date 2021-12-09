package com.example.myassayment.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myassayment.R
import com.example.myassayment.databinding.FragmentBookDateBinding
import com.example.myassayment.databinding.FragmentSplashScreenBinding


class SplashScreenFragment : Fragment() {
    private var _binding: FragmentSplashScreenBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentSplashScreenBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}