package com.example.myassayment.ui.body.booking

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.example.myassayment.R
import com.example.myassayment.adapters.DateAdapter
import com.example.myassayment.databinding.FragmentBookDateBinding
import com.example.myassayment.databinding.FragmentDateBinding
import com.example.myassayment.models.Client
import com.example.myassayment.models.Doctor
import com.example.myassayment.models.TimeSchedule
import com.example.myassayment.utils.Constants
import com.example.myassayment.utils.StatusResult
import com.example.myassayment.viewmodels.BookingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DateFragment : Fragment(), DateAdapter.DataSchedulementItemClick {
    private var _binding: FragmentDateBinding?=null
    private val binding get() = _binding!!
    private lateinit var doctor:Doctor
    private var timeSelected:TimeSchedule?=null
    private val bookingViewModel by viewModels<BookingViewModel>()
    private val doctorSchedulaAdapter by lazy { DateAdapter(this) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentDateBinding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    private fun setUp() {
        lifecycleScope.launch {
            val doctorId:String?=arguments?.getString(Constants.KEY_BUNDLE_PUT_DOCTOR_ID)
            delay(200)
            bookingViewModel.getDoctorById(doctorId!!)
            bookingViewModel.getDoctorSchedula(doctorId)
        }
        lifecycleScope.launchWhenStarted {
            bookingViewModel.doctorById.collectLatest {
                when{
                    it is StatusResult.OnError ->{

                    }
                    it is StatusResult.OnSuccess ->{
                        doctor=it.data!!
                        setUpView()
                    }
                    it is StatusResult.OnLoading ->{

                    }
                }
            }
        }
        bookingViewModel.dateScedulaDoctor.observe(viewLifecycleOwner,{
            when{
                it is StatusResult.OnError ->{

                }
                it is StatusResult.OnSuccess ->{
                    doctorSchedulaAdapter.setData(it.data!!)
                }
                it is StatusResult.OnLoading ->{

                }
            }
        })
    }

    private fun setUpView() {
        setUpRecy()
        binding.imgDoctorinfoDatefrag.load(doctor.photo)
        binding.nameTitleDoctorInfoDatafrag.text=doctor.nameEN
        binding.spaitaltyDoctorInfoDatafrag.text=doctor.spicialty
        binding.imgDoctorinfoDatefrag.load(doctor.photo)
        binding.priceDoctorInfoDatafrag.text="${doctor.priceFees} $"
        binding.followDoctorInfoDatafrag.text="${doctor.priceFollowUp} $"

        binding.fabNextScreenDatefrag.setOnClickListener {
            if (timeSelected==null)
            {
                Toast.makeText(requireActivity(), "Please Select Date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val action=DateFragmentDirections.actionDateFragmentToTimeFragment(
                doctor,
                timeSelected!!
            )
            findNavController().navigate(action)
        }

    }

    private fun setUpRecy() {
        binding.recyDateFrag.apply {
            adapter=doctorSchedulaAdapter
            layoutManager=GridLayoutManager(requireActivity(),2)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    override fun itemClick(time: TimeSchedule) {
        timeSelected=time
    }


}




