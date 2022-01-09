package com.example.myassayment.ui.body.bookingfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myassayment.R
import com.example.myassayment.databinding.FragmentCancleBookingBinding
import com.example.myassayment.models.Appointeiment
import com.example.myassayment.utils.MyUtils
import com.example.myassayment.utils.StatusResult
import com.example.myassayment.viewmodels.BookingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CancleBookingFragment : DialogFragment() {
    private var _binding: FragmentCancleBookingBinding? = null
    private val binding get() = _binding!!
    private  val bookingViewModel by viewModels<BookingViewModel>()
    private val navArgs by navArgs<CancleBookingFragmentArgs>()
    private lateinit var myAppointement:Appointeiment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentCancleBookingBinding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    private fun setUp() {
        myAppointement=navArgs.myappointemetn
        binding.btnCanleCanclebooking.setOnClickListener {
            bookingViewModel.deltelAppointement(appointeiment = myAppointement)
        }
        binding.btnKeepBookingCanclebooking.setOnClickListener {
            findNavController().popBackStack()
        }
        bookingViewModel.delteAppointement.observe(viewLifecycleOwner,{
            when{
                it is StatusResult.OnSuccess->{
                    MyUtils.toastSuccessBooking(requireActivity(),"booking cnacle")
                    findNavController().navigate(R.id.action_cancleBookingFragment_to_bookingFragment)
                }
                it is StatusResult.OnLoading ->{

                }
                it is StatusResult.OnError ->{
                    Toast.makeText(requireContext(), "${it.msg}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}