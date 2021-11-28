package com.example.myassayment.ui.body.booking

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myassayment.R
import com.example.myassayment.databinding.FragmentBookDateBinding
import com.example.myassayment.databinding.FragmentDateBinding
import com.example.myassayment.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DateFragment : Fragment() {
    private var _binding: FragmentDateBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentDateBinding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    private fun setUp() {
        val doctorId=arguments?.getString(Constants.KEY_BUNDLE_PUT_DOCTOR_ID)
        Log.d("mylog",doctorId.toString())

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}