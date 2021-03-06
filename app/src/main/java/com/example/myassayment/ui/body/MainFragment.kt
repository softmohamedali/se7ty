package com.example.myassayment.ui.body

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myassayment.R
import com.example.myassayment.adapters.BestDoctorsItemAdapter
import com.example.myassayment.adapters.MyUpcomingBookingItemAdapter
import com.example.myassayment.adapters.ServicesItemadapter
import com.example.myassayment.databinding.FragmentMainBinding
import com.example.myassayment.models.Appointeiment
import com.example.myassayment.models.Doctor
import com.example.myassayment.models.Sevices
import com.example.myassayment.utils.MyUtils
import com.example.myassayment.utils.StatusResult
import com.example.myassayment.viewmodels.BookingViewModel
import com.example.myassayment.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainFragment :
    Fragment() ,
    ServicesItemadapter.TodyAppointementItemClick,
    BestDoctorsItemAdapter.BestDoctorItemClick,
        MyUpcomingBookingItemAdapter.UpComBookingItemClick
{
    private var _binding: FragmentMainBinding?=null
    private val binding get() = _binding!!
    private val latestServicesadapter by lazy { ServicesItemadapter(this) }
    private val bestDoctoradapter by lazy { BestDoctorsItemAdapter(this) }
    private val upcombookingadapter by lazy { MyUpcomingBookingItemAdapter(this) }
    private val homeViewModel by viewModels<HomeViewModel>()
    private  val bookingViewModel by viewModels<BookingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentMainBinding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    private fun setUp() {
        setLatestServicesRecy()
        setBestDoctorRecy()
        setUpCombookingRecy()
        binding.cardBookingdateMian.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_doctorAppointementFragment)
        }
        homeViewModel.getDoctors()
        bookingViewModel.getUserAppointement()
        setUpObservers()
    }

    private fun setUpObservers() {
        lifecycleScope.launchWhenStarted {
            bookingViewModel.myAppointement.collect {
                when{
                    it is StatusResult.OnError ->{
                        showUpCommingBookingStuff(false)
                    }
                    it is StatusResult.OnLoading ->{
                        showUpCommingBookingStuff(false)
                    }
                    it is StatusResult.OnSuccess ->{
                        if (it.data!!.isEmpty())
                        {
                            showUpCommingBookingStuff(false)
                        }else{
                            upcombookingadapter.setData(it.data!!)
                            showUpCommingBookingStuff(true)
                        }

                    }
                }
            }
        }
        homeViewModel.doctor.observe(viewLifecycleOwner,{
            when{
                it is StatusResult.OnError ->{
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                }
                it is StatusResult.OnLoading -> {
                    Toast.makeText(requireContext(), "loading", Toast.LENGTH_SHORT).show()
                }
                it is StatusResult.OnSuccess ->{
                    bestDoctoradapter.setData(it.data!!)
                }
            }
        })
    }

    private fun setBestDoctorRecy() {
        binding.bestDoctorRecy.apply {
            adapter=bestDoctoradapter
            layoutManager=LinearLayoutManager(requireActivity(),RecyclerView.HORIZONTAL,false)
        }
    }

    private fun setUpCombookingRecy() {
        binding.upcomBookingRecy.apply {
            adapter=upcombookingadapter
            layoutManager=LinearLayoutManager(requireActivity(),RecyclerView.HORIZONTAL,false)
        }
    }

    private fun setLatestServicesRecy() {
        val list= mutableListOf<Sevices>()
        list.add(Sevices("Covid_19",R.drawable.img22))
        list.add(Sevices("blood bank",R.drawable.img1))
        latestServicesadapter.setData(list)
        binding.latestServicesRecy.apply {
            adapter=latestServicesadapter
            layoutManager=LinearLayoutManager(requireActivity(),RecyclerView.HORIZONTAL,false)
        }
    }

    override fun itembestDoctorClick(doctor: Doctor) {
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToDoctorInfoFragment(doctor))
    }
    override fun bookBtnClick(doctor: Doctor) {
        if (homeViewModel.currntuser()==null){
            MyUtils.toastwarningBooking(
                requireContext(),
                "You Should Login to get Permission to this operation"
            )
        }else{
            findNavController()
                .navigate(MainFragmentDirections.actionMainFragmentToBookDateFragment(doctor))
        }
    }

    override fun itemClick(services: Sevices, pos: Int) {
        if (pos==0){
            findNavController().navigate(R.id.action_mainFragment_to_covid19Fragment)
        }else if(pos==1){
            findNavController().navigate(R.id.action_mainFragment_to_lapTestFragment)
        }else{

        }
    }

    override fun itemupCommingBookingClick(upcomBooking: Appointeiment) {
        val action=MainFragmentDirections.actionMainFragmentToShowBookingDetailsFragment(upcomBooking)
        findNavController().navigate(action)
    }

    private fun showUpCommingBookingStuff(show:Boolean)
    {
        if (show)
        {
            binding.upcomBookingRecy.visibility=View.VISIBLE
            binding.tvUpcomm.visibility=View.VISIBLE
        }else{
            binding.upcomBookingRecy.visibility=View.GONE
            binding.tvUpcomm.visibility=View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}