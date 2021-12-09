package com.example.myassayment.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myassayment.R
import com.example.myassayment.databinding.FragmentBookDateBinding
import com.example.myassayment.databinding.FragmentSingUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingUpFragment : Fragment() {
    private var _binding: FragmentSingUpBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentSingUpBinding.inflate(layoutInflater)
        return inflater.inflate(R.layout.fragment_sing_up, container, false)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}