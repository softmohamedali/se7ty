package com.example.myassayment.ui.body.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.myassayment.R
import com.example.myassayment.databinding.FragmentMedicationBinding
import com.example.myassayment.databinding.FragmentViewAttachementBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewAttachementFragment : Fragment() {
    private var _binding: FragmentViewAttachementBinding?=null
    private val binding get() = _binding!!
    private val navArgs by navArgs<ViewAttachementFragmentArgs>()
    private lateinit var img:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentViewAttachementBinding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    private fun setUp() {
        img=navArgs.img
        binding.imgAttachement.load(img)
        binding.imageBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}