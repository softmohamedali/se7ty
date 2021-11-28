package com.example.myassayment.ui.body

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myassayment.R
import com.example.myassayment.adapters.BestDoctorsItemAdapter
import com.example.myassayment.adapters.ServicesItemadapter
import com.example.myassayment.databinding.FragmentMainBinding
import com.example.myassayment.models.Doctor
import com.example.myassayment.models.Sevices
import com.example.myassayment.utils.StatusResult
import com.example.myassayment.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment :
    Fragment() ,
    ServicesItemadapter.TodyAppointementItemClick,
    BestDoctorsItemAdapter.BestDoctorItemClick
{
    private var _binding: FragmentMainBinding?=null
    private val binding get() = _binding!!
    private val latestServicesadapter by lazy { ServicesItemadapter(this) }
    private val bestDoctoradapter by lazy { BestDoctorsItemAdapter(this) }

    private val homeViewModel by viewModels<HomeViewModel>()
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
        homeViewModel.getDoctors()
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

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }


    override fun itembestDoctorClick(doctor: Doctor) {
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToDoctorInfoFragment(doctor))
    }
    override fun bookBtnClick(doctor: Doctor) {
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToBookDateFragment(doctor))
    }

    override fun itemClick(services: Sevices, pos: Int) {
        if (pos==0){

        }else{

        }
    }

}