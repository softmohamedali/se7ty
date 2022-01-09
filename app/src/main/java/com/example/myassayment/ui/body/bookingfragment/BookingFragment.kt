package com.example.myassayment.ui.body.bookingfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myassayment.R
import com.example.myassayment.adapters.AppointementItemAdapter
import com.example.myassayment.databinding.FragmentBookingBinding
import com.example.myassayment.models.Appointeiment
import com.example.myassayment.utils.StatusResult
import com.example.myassayment.viewmodels.BookingViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class BookingFragment : Fragment(),AppointementItemAdapter.AppointemtnBookingItemClick {
    private var _binding: FragmentBookingBinding? = null
    private val binding get() = _binding!!
    private  val bookingViewModel by viewModels<BookingViewModel>()
    private val appointementItemAdapter by lazy { AppointementItemAdapter(this) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentBookingBinding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    private fun setUp() {
        setUpRecy()
        setUpObservers()
        bookingViewModel.getUserAppointement()
        binding.btnBookKnowBooking.setOnClickListener {
            findNavController().navigate(R.id.action_bookingFragment_to_doctorAppointementFragment)
        }
        binding.btnGotoPreOrCancBooking.setOnClickListener {
            bookingViewModel.getAllPreBooking.observe(viewLifecycleOwner,{
                if (it.size>0){
                    findNavController().navigate(R.id.action_bookingFragment_to_previousBookingFragment)
                }else{
                    Snackbar.make(binding.root,"No Preivous Booking", Snackbar.LENGTH_SHORT)
                        .show()
                }
            })

        }
    }

    private fun setUpRecy() {
        binding.recyMyappontimentBooking.apply {
            adapter=appointementItemAdapter
            layoutManager=LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL,false)
        }
    }

    private fun setUpObservers() {
        lifecycleScope.launchWhenStarted {
            bookingViewModel.myAppointement.collect {
                when{
                    it is StatusResult.OnError ->{
                        showContainerRecy(false)
                    }
                    it is StatusResult.OnLoading ->{
                        showContainerRecy(false)
                    }
                    it is StatusResult.OnSuccess ->{
                        showContainerRecy(true)
                        appointementItemAdapter.setData(it.data!!)
                    }
                }
            }
        }
    }

    fun showContainerRecy(show:Boolean)
    {
        if (show){
            binding.containerRecy.visibility=View.VISIBLE
            binding.containerNoapponitement.visibility=View.GONE
        }else{
            binding.containerRecy.visibility=View.GONE
            binding.containerNoapponitement.visibility=View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun itembestDoctorClick(appointeiment: Appointeiment) {
        val action=BookingFragmentDirections
            .actionBookingFragmentToShowBookingDetailsFragment(appointeiment)
        findNavController().navigate(action)
    }


}