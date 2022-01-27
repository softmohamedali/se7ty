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
import com.example.myassayment.databinding.FragmentMedicalTestBinding
import com.example.myassayment.databinding.FragmentMyElecticalRecordBinding
import com.example.myassayment.models.MedicalTest
import com.example.myassayment.utils.StatusResult
import com.example.myassayment.viewmodels.AuthViewModel
import com.example.myassayment.viewmodels.ServicesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MedicalTestFragment : Fragment(),MedicalTestItemAdapter.MedicalTestItemClick {
    private var _binding: FragmentMedicalTestBinding? = null
    private val authViewModel by viewModels<AuthViewModel>()
    private val medicalTestAddapter  by lazy { MedicalTestItemAdapter(this) }
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMedicalTestBinding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    private fun setUp() {
        setUpView()
        authViewModel.getUserMedicalTest()
        setUpObserver()
    }

    private fun setUpView() {
        setUpRecy()
        binding.fabAdd.setOnClickListener {
            val action=MedicalTestFragmentDirections
                .actionMedicalTestFragmentToAddMedicalTestFragment(null)
            findNavController().navigate(action)
        }
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUpRecy() {
        binding.recyMymedicalTest.apply {
            adapter=medicalTestAddapter
            layoutManager=LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

    private fun setUpObserver() {
        lifecycleScope.launchWhenStarted {
            authViewModel.medicalTestUser.collect {
                when {
                    it is StatusResult.OnLoading -> {
                        showLoading(true)
                        showError(false)
                    }
                    it is StatusResult.OnError -> {
                        showLoading(false)
                        showError(true,msg = "No Medical Test yet Add your first ?")
                        medicalTestAddapter.setData(mutableListOf())
                    }
                    it is StatusResult.OnSuccess -> {
                        showLoading(false)
                        showError(false)
                        medicalTestAddapter.setData(it.data!!)
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
            binding.pbMedicaltest.isVisible=true
            binding.viewMedicalTest.setBackgroundColor(
                ContextCompat
                    .getColor(requireContext(), R.color.blackop))
            binding.continerUserMedicalTest.isActivated=false
        }else{
            binding.pbMedicaltest.isVisible=false
            binding.viewMedicalTest.setBackgroundColor(
                ContextCompat
                    .getColor(requireContext(), R.color.none))
            binding.continerUserMedicalTest.isActivated=true
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun btnViewAttachmentClick(img: String) {
        val action=MedicalTestFragmentDirections
            .actionMedicalTestFragmentToViewAttachementFragment(img)
        findNavController().navigate(action)
    }

    override fun btnEditClick(medicalTest: MedicalTest) {
        val action=MedicalTestFragmentDirections
            .actionMedicalTestFragmentToAddMedicalTestFragment(medicalTest)
        findNavController().navigate(action)
    }
}