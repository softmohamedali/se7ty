package com.example.myassayment.ui.body

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.myassayment.R
import com.example.myassayment.databinding.FragmentDoctorInfoBinding
import com.example.myassayment.databinding.FragmentMainBinding
import com.example.myassayment.models.Doctor
import com.example.myassayment.utils.MyUtils
import com.example.myassayment.viewmodels.BookingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorInfoFragment : Fragment() {
    private var _binding: FragmentDoctorInfoBinding?=null
    private val binding get() = _binding!!
    private val navargs by navArgs<DoctorInfoFragmentArgs>()
    private lateinit var doctor:Doctor
    private  val bookingViewModel by viewModels<BookingViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentDoctorInfoBinding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    private fun setUp() {
        doctor=navargs.doctor
        setUpView()
    }

    private fun setUpView() {
        binding.imgDoctorinfo.load(doctor.photo)
        binding.nameTitleDoctorInfo.text=doctor.nameEN
        binding.spaitaltyDoctorInfo.text=doctor.spicialty
        binding.priceDoctorInfo.text="${doctor.priceFees} $"
        binding.followDoctorInfo.text="${doctor.priceFollowUp} $"
        binding.briefDoctorInfo.text=doctor.descriptionEn
        binding.bookBtnDoctorInfo.setOnClickListener {
            if (bookingViewModel.currntuser()==null){
                MyUtils.toastwarningBooking(
                    requireContext(),
                    "You Should Login to get Permission to this operation"
                )
            }else{
                val action=DoctorInfoFragmentDirections
                    .actionDoctorInfoFragmentToBookDateFragment(doctor)
                findNavController().navigate(action)
            }
        }
        binding.btnBackDoctorinfo.setOnClickListener {
            findNavController().popBackStack()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}