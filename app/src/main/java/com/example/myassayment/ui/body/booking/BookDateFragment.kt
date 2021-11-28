package com.example.myassayment.ui.body.booking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.example.myassayment.R
import com.example.myassayment.databinding.FragmentBookDateBinding
import com.example.myassayment.models.Doctor
import com.example.myassayment.utils.Constants
import dagger.hilt.android.AndroidEntryPoint




@AndroidEntryPoint
class BookDateFragment : Fragment() {
    private var _binding: FragmentBookDateBinding?=null
    private val binding get() = _binding!!
    private val navargs by navArgs<BookDateFragmentArgs>()
    private lateinit var doctor: Doctor


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentBookDateBinding.inflate(layoutInflater)
        doctor=navargs.doctor
        setDoctorIdToStartDestination()
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
        val bookingNavController = bookingNavHost.navController
        bookingNavController.setGraph(R.navigation.booking_nav, bundle)
    }

    private fun setUpView() {
        binding
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}