package com.example.myassayment.ui.body

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.myassayment.R
import com.example.myassayment.databinding.FragmentCancleEmrgencyBinding
import com.example.myassayment.databinding.FragmentWaitEmergencyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CancleEmrgencyFragment : DialogFragment() {
    private var _binding: FragmentCancleEmrgencyBinding? = null
    private val binding get() = _binding!!
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)

        return dialog
    }
    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            dialog.window!!.setLayout(
                (getResources().getDisplayMetrics().widthPixels*0.80).toInt(),
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentCancleEmrgencyBinding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    private fun setUp() {
        binding.btnCanleCancleemergency.setOnClickListener {
            findNavController().navigate(R.id.action_cancleEmrgencyFragment_to_emergencyFragment)
        }
        binding.btnKeepInqueueCancleemergency.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}