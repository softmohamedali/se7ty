package com.example.myassayment.ui.body.bookingfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myassayment.R
import com.example.myassayment.adapters.PreBookingItemAdapter
import com.example.myassayment.data.local.Entitys.PreviousBooking
import com.example.myassayment.databinding.FragmentBookingBinding
import com.example.myassayment.databinding.FragmentLogInBinding
import com.example.myassayment.databinding.FragmentPreviousBookingBinding
import com.example.myassayment.viewmodels.BookingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreviousBookingFragment : Fragment() {
    private var _binding: FragmentPreviousBookingBinding? = null
    private val binding get() = _binding!!
    private  val bookingViewModel by viewModels<BookingViewModel>()
    private val preBookItemAdapter by lazy { PreBookingItemAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentPreviousBookingBinding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    private fun setUp() {
        setUpRecy()
        binding.btnBackPrebooking.setOnClickListener {
            findNavController().popBackStack()
        }
        bookingViewModel.getAllPreBooking.observe(viewLifecycleOwner,{
            preBookItemAdapter.setData(it as MutableList<PreviousBooking>)
        })
    }

    private fun setUpRecy() {
        binding.recyPrebooking.apply {
            adapter=preBookItemAdapter
            layoutManager=LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL,false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}