package com.example.myassayment.ui.body.booking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.myassayment.R
import com.example.myassayment.databinding.FragmentConfirmBookingBinding
import com.example.myassayment.databinding.FragmentDateBinding
import com.example.myassayment.models.Doctor
import com.example.myassayment.models.TimeSchedule
import com.example.myassayment.utils.MyUtils
import com.example.myassayment.utils.StatusResult
import com.example.myassayment.viewmodels.BookingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmBookingFragment : Fragment() {
    private var _binding: FragmentConfirmBookingBinding?=null
    private val binding get() = _binding!!
    private val navArgs by navArgs<ConfirmBookingFragmentArgs>()
    private val bookingViewModel by viewModels<BookingViewModel>()
    private lateinit var doctor:Doctor
    private lateinit var timeSelected:TimeSchedule
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentConfirmBookingBinding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    private fun setUp() {
        doctor=navArgs.doctor
        timeSelected=navArgs.timeSelected
        setUpView()
        bookingViewModel.bookAppointement.observe(viewLifecycleOwner,{
            when{
                it is StatusResult.OnSuccess ->{
                    binding.pbConfirmBooking.isVisible=false
                    binding.floorConfirmfrag.isVisible=true
                    MyUtils.toastSuccessBooking(requireActivity())
                }
                it is StatusResult.OnError ->{
                    binding.pbConfirmBooking.isVisible=false
                    binding.floorConfirmfrag.isVisible=false
                    Toast.makeText(requireActivity(), "${it.msg}", Toast.LENGTH_SHORT).show()
                }
                it is StatusResult.OnLoading ->{
                    binding.pbConfirmBooking.isVisible=true
                    binding.floorConfirmfrag.isVisible=true
                }
            }
        })
    }

    private fun setUpView() {
        binding.dateEtConfirmfrag.text="${timeSelected.date}"
        binding.feesEtConfirmfrag.text="${doctor.priceFees} $"
        binding.timeEtConfirmfrag.text="${timeSelected.fromTime}:${timeSelected.toTime}"
        binding.spaitaltyDoctorInfoConfirmfrag.text=doctor.spicialty
        binding.nameTitleDoctorInfoConfirmfrag.text=doctor.nameEN
        binding.imgDoctorinfoConfirmfrag.load(doctor.photo)
        binding.fabBackConfirmfrag.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.fabConfirmConfirmfrag.setOnClickListener {
            bookingViewModel.bookDate(doctor.doctorsId!!,timeSelected,)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}