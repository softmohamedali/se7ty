package com.example.myassayment.ui.body.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myassayment.R
import com.example.myassayment.adapters.MedicalTestItemAdapter
import com.example.myassayment.adapters.MedicationItemAdapter
import com.example.myassayment.databinding.FragmentMedicationBinding
import com.example.myassayment.databinding.FragmentMyElecticalRecordBinding
import com.example.myassayment.models.Medecation
import com.example.myassayment.utils.StatusResult
import com.example.myassayment.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MedicationFragment : Fragment(),MedicationItemAdapter.MedicationItemClick {
    private var _binding: FragmentMedicationBinding?=null
    private val authViewModel by viewModels<AuthViewModel>()
    private val medicationAddapter  by lazy { MedicationItemAdapter(this) }
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentMedicationBinding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    private fun setUp() {
        setUpView()
        authViewModel.getUserMedication()
        setUpObserver()
    }

    private fun setUpView() {
        setUpRecy()
        binding.fabAdd.setOnClickListener {
            val action=MedicationFragmentDirections
                .actionMedicationFragmentToAddMedicationFragment(null)
            findNavController().navigate(action)
        }
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUpRecy() {
        binding.recyMedication.apply {
            adapter=medicationAddapter
            layoutManager= LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

    private fun setUpObserver() {
        lifecycleScope.launchWhenStarted {
            authViewModel.medicationUser.collect {
                when {
                    it is StatusResult.OnLoading -> {
                        showLoading(true)
                        showError(false)
                    }
                    it is StatusResult.OnError -> {
                        showLoading(false)
                        showError(true,msg = "No Medical Test yet Add your first ?")
                        medicationAddapter.setData(mutableListOf())
                    }
                    it is StatusResult.OnSuccess -> {
                        showLoading(false)
                        showError(false)
                        medicationAddapter.setData(it.data!!)
                    }
                }
            }
        }
    }

    fun showError(show: Boolean,msg:String="") {
        binding.imgError.isVisible = show
        binding.tvError.isVisible = show
        binding.tvError.text=msg
    }

    private fun showLoading(show:Boolean)
    {
        if (show){
            binding.pbMedication.isVisible=true
            binding.viewMedicationTest.setBackgroundColor(
                ContextCompat
                    .getColor(requireContext(), R.color.blackop))
        }else{
            binding.pbMedication.isVisible=false
            binding.viewMedicationTest.setBackgroundColor(
                ContextCompat
                    .getColor(requireContext(), R.color.none))
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    override fun btnEditClick(medication: Medecation) {
        val action=MedicationFragmentDirections.actionMedicationFragmentToAddMedicationFragment(medication)
        findNavController().navigate(action)
    }
}