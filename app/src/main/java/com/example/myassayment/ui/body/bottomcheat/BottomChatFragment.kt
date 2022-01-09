package com.example.myassayment.ui.body.bottomcheat

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.navigation.fragment.findNavController
import com.example.myassayment.R
import com.example.myassayment.databinding.FragmentBottomChatBinding
import com.example.myassayment.databinding.FragmentDoctorInfoBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomChatFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentBottomChatBinding?=null
    private val binding get() = _binding!!
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog=super.onCreateDialog(savedInstanceState)
        bottomSheetDialog.setOnShowListener {
            val dialoc=it as BottomSheetDialog
            val bottomSheet=dialoc.findViewById<FrameLayout>(R.id.design_bottom_sheet)
            val bottomSheetBehavior= BottomSheetBehavior.from(bottomSheet!!)
            bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels)
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
        }
        return bottomSheetDialog
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentBottomChatBinding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    private fun setUp() {
        binding.btnGethelpBootomchat.setOnClickListener {
            findNavController().navigate(R.id.action_bottomChatFragment_to_getHelpFragment)
        }
        binding.btnAskdoctorBottomchat.setOnClickListener {
            findNavController().navigate(R.id.action_bottomChatFragment_to_askDoctorFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}