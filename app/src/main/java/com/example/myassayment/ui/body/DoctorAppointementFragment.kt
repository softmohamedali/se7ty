package com.example.myassayment.ui.body

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myassayment.R
import com.example.myassayment.adapters.SpeitlityItemAdapter
import com.example.myassayment.databinding.FragmentDoctorAppointementBinding
import com.example.myassayment.databinding.FragmentDoctorInfoBinding
import com.example.myassayment.models.Speitality
import com.example.myassayment.utils.StatusResult
import com.example.myassayment.viewmodels.DoctorAppointmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorAppointementFragment : Fragment(),SpeitlityItemAdapter.SpitialItemClick {
    private var _binding: FragmentDoctorAppointementBinding?=null
    private val binding get() = _binding!!
    private val doctorAppViewModel by viewModels<DoctorAppointmentViewModel>()
    private val speialityAdapter  by lazy { SpeitlityItemAdapter(this) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentDoctorAppointementBinding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    private fun setUp() {
        binding.btnBackDoctoriappo.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnSpetilityAndrology.setOnClickListener {
            navigateToDoctorList("Andrology")
        }
        binding.btnSpetilityPediatrics.setOnClickListener {
            navigateToDoctorList("Pediatrics")
        }
        binding.btnSpetilityPsychiatry.setOnClickListener {
            navigateToDoctorList("Psychiatry")
        }
        binding.btnSpetilityDite.setOnClickListener {
            navigateToDoctorList("Diet and Nutriation")
        }
        binding.etSearchSpecialityDoctorapp.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrEmpty())
            {
                doctorAppViewModel.getSpeialits()
                showSpContainer(true)
            }else{
                doctorAppViewModel.getSearchSpeialits(text.toString().trim().toString())
                showSpContainer(false)
            }
        }
        setUpRecy()
        doctorAppViewModel.getSpeialits()
        doctorAppViewModel.speialits.observe(viewLifecycleOwner,{
            when{
                it is StatusResult.OnError ->{
                    Toast.makeText(requireContext(), "${it.msg}", Toast.LENGTH_SHORT).show()
                    speialityAdapter.setData(mutableListOf())
                }
                it is StatusResult.OnLoading -> {

                }
                it is StatusResult.OnSuccess ->{
                    speialityAdapter.setData(it.data!!)
                }
            }
        })
    }

    private fun setUpRecy() {
        binding.recySpeility.apply {
            adapter=speialityAdapter
            layoutManager=LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL,false)
        }
    }

    private fun navigateToDoctorList(speiality: String)
    {
        val action=DoctorAppointementFragmentDirections.
        actionDoctorAppointementFragmentToDoctorListFragment(speiality)
        findNavController().navigate(action)
    }

    override fun itembestSpeitalityClick(mspeiality: String) {
        navigateToDoctorList(mspeiality)
    }

    fun showSpContainer(show:Boolean){
        if (show)
        {
            binding.spContainer.visibility=View.VISIBLE
        }else{
            binding.spContainer.visibility=View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }



}