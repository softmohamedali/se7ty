package com.example.myassayment.ui.body.booking

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myassayment.R
import com.example.myassayment.databinding.FragmentBookDateBinding
import com.example.myassayment.models.Doctor
import com.example.myassayment.utils.Constants
import com.kofigyan.stateprogressbar.StateProgressBar
import dagger.hilt.android.AndroidEntryPoint




@AndroidEntryPoint
class BookDateFragment : Fragment(),NavController.OnDestinationChangedListener {
    private var _binding: FragmentBookDateBinding?=null
    private val binding get() = _binding!!
    private val navargs by navArgs<BookDateFragmentArgs>()
    private lateinit var doctor: Doctor
    private lateinit var bookingNavController:NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentBookDateBinding.inflate(layoutInflater)
        doctor=navargs.doctor
        Log.d("mylog","first"+doctor.doctorsId.toString())
        setDoctorIdToStartDestination()
        bookingNavController.addOnDestinationChangedListener(this)
        setUp()
        return binding.root
    }

    private fun setUp() {
        setUpView()

    }
    private fun setDoctorIdToStartDestination()
    {
        val bundle = Bundle()
        bundle.putString(Constants.KEY_BUNDLE_PUT_DOCTOR_ID,doctor.doctorsId)
        val bookingNavHost =
            childFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        bookingNavController = bookingNavHost.navController
        bookingNavController.setGraph(R.navigation.booking_nav, bundle)
    }

    private fun setUpView() {
        binding.btnBackBookdate.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when(destination.id)
        {
            R.id.dateFragment->{
                binding.progress3.setCurrentStateNumber(StateProgressBar.StateNumber.ONE)
            }
            R.id.timeFragment ->{
                binding.progress3.setCurrentStateNumber(StateProgressBar.StateNumber.TWO)
            }
        }
    }

}






















//        val statusDes= arrayOf("request\nis done","in\nshipping","on the\nway"," delivered\nhanded")
//        binding.progress3.setStateDescriptionData(statusDes)
//        binding.progress3.setStateNumber(myPay.statusPayments!!,myView.progress3)

