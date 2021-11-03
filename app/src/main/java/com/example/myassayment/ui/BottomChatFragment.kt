package com.example.myassayment.ui

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.myassayment.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomChatFragment : BottomSheetDialogFragment() {

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_chat, container, false)
    }


}