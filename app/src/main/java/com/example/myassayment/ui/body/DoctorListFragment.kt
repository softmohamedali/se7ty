package com.example.myassayment.ui.body

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myassayment.R
import com.example.myassayment.adapters.DoctorItemAdapter
import com.example.myassayment.databinding.FragmentDoctorInfoBinding
import com.example.myassayment.databinding.FragmentDoctorListBinding
import com.example.myassayment.models.Doctor
import com.example.myassayment.utils.StatusResult
import com.example.myassayment.viewmodels.DoctorAppointmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorListFragment : Fragment(),DoctorItemAdapter.BestDoctorItemClick {
    private var _binding: FragmentDoctorListBinding?=null
    private val binding get() = _binding!!
    private val navargs by navArgs<DoctorListFragmentArgs>()
    private lateinit var sepiality:String
    private val doctorListAdapter by lazy { DoctorItemAdapter(this) }
    private val doctorAppoViewModel by viewModels<DoctorAppointmentViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentDoctorListBinding.inflate(layoutInflater)
        sepiality=navargs.doctorSpetiality
        setUp()
        return binding.root
    }

    private fun setUp() {
        binding.tvLabelDoctorlist.text=sepiality
        setUpReccy()
        binding.btnBackDoctorlist.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.etSearchDoctorList.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrEmpty())
            {
                doctorAppoViewModel.getDoctorWithSpetility(spetitlity = sepiality)
            }else{
                doctorAppoViewModel.getDoctorSearchByName(text.toString())
            }
        }
        doctorAppoViewModel.getDoctorWithSpetility(spetitlity = sepiality)
        doctorAppoViewModel.doctorWSpetitlity.observe(viewLifecycleOwner){
            when{
                it is StatusResult.OnError ->{
                    Toast.makeText(requireContext(), "${it.msg}", Toast.LENGTH_SHORT).show()
                    doctorListAdapter.setData(mutableListOf())
                }
                it is StatusResult.OnLoading -> {

                }
                it is StatusResult.OnSuccess ->{
                    doctorListAdapter.setData(it.data!!)
                }
            }
        }
        doctorAppoViewModel.doctorSearchByName.observe(viewLifecycleOwner){
            when{
                it is StatusResult.OnError ->{
                    Toast.makeText(requireContext(), "${it.msg}", Toast.LENGTH_SHORT).show()
                    doctorListAdapter.setData(mutableListOf())
                }
                it is StatusResult.OnLoading -> {

                }
                it is StatusResult.OnSuccess ->{
                    doctorListAdapter.setData(it.data!!)
                }
            }
        }
    }

    private fun setUpReccy() {
        binding.recyDoctorList.apply {
            layoutManager=LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL,false)
            adapter=doctorListAdapter
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    override fun itembestDoctorClick(doctor: Doctor) {
        val action=DoctorListFragmentDirections.actionDoctorListFragmentToDoctorInfoFragment(doctor)
        findNavController().navigate(action)
    }

    override fun bookBtnClick(doctor: Doctor) {
        val action=DoctorListFragmentDirections.actionDoctorListFragmentToBookDateFragment(doctor)
        findNavController().navigate(action)
    }

}