package com.example.myassayment.ui.body.bookingfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.myassayment.databinding.FragmentShowBookingDetailsBinding
import com.example.myassayment.models.Appointeiment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowBookingDetailsFragment : Fragment() {
    private var _binding: FragmentShowBookingDetailsBinding? = null
    private val binding get() = _binding!!
    private val navArgs by navArgs<ShowBookingDetailsFragmentArgs>()
    private lateinit var myAppointement:Appointeiment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentShowBookingDetailsBinding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    private fun setUp() {
        myAppointement=navArgs.myappointement
        setUpView()

    }

    private fun setUpView() {
        binding.imgShowApponitement.load(myAppointement.doctor?.photo){
        }
        binding.nameTitleShowApponitement.text=myAppointement.doctor?.nameEN
        binding.spaitaltyShowApponitement.text=myAppointement.doctor?.spicialty
        binding.tvDateShowApponitement.text=myAppointement.timeBook
        binding.tvPymentstatusShowApponitement.text="unPay"
        binding.bookingFeesShowApponitement.text="Bookin fees ${myAppointement.doctor?.priceFees}"
        binding.btnCopyCode.setOnClickListener {

        }
        binding.doctorBiogrphyShowApponitement.text=myAppointement.doctor?.spicialty
        binding.btnCancleShowShowApponitement.setOnClickListener {
            val action=ShowBookingDetailsFragmentDirections
                .actionShowBookingDetailsFragmentToCancleBookingFragment(myAppointement)
            findNavController().navigate(action)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}