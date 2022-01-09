package com.example.myassayment.ui.body.serviesesfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myassayment.R
import com.example.myassayment.adapters.LapTestsItemAdapter
import com.example.myassayment.databinding.FragmentLapTestBinding
import com.example.myassayment.databinding.FragmentServicesBinding
import com.example.myassayment.models.LapTests
import com.example.myassayment.models.ListLapTests
import com.example.myassayment.utils.StatusResult
import com.example.myassayment.viewmodels.ServicesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LapTestFragment : Fragment(), LapTestsItemAdapter.LapTestsItemClick{
    private var _binding: FragmentLapTestBinding?=null
    private val binding get() = _binding!!
    private val lapTestsAdapter by lazy { LapTestsItemAdapter(this) }
    private val servicesViewModel by viewModels<ServicesViewModel>()
    private lateinit var listLapTests:ListLapTests
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentLapTestBinding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    private fun setUp() {
        binding.tvCountLaptests.text="Lap tests cheaked : 0"
        setUpRecy()
        servicesViewModel.getAllLapTests()
        setUpObservers()
        binding.fabGotoAddmoreLaptests.setOnClickListener {
            if (lapTestsAdapter.getLapTestCheaked().size>0)
            {
                val action=LapTestFragmentDirections
                    .actionLapTestFragmentToAddMoreLapTestsDetailsFragment(listLapTests)
                findNavController().navigate(action)
            }
        }
        binding.imgCloseLaptests.setOnClickListener {
            findNavController().popBackStack()
        }
//        binding.etSearchTestsLaptest.doOnTextChanged { text, start, before, count ->
//            servicesViewModel.getSearchLapTests(text.toString())
//        }
    }

    private fun setUpObservers() {
        lifecycleScope.launchWhenStarted {
            servicesViewModel.lapTests.collect {
                when{
                    it is StatusResult.OnLoading->{
                        showProgress(true)
                    }
                    it is StatusResult.OnError->{
                        showProgress(false)
                        Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
                    }
                    it is StatusResult.OnSuccess->{
                        showProgress(false)
                        lapTestsAdapter.setData(it.data!!)
                    }
                }
            }
        }
    }

    private fun setUpRecy() {
        binding.recyTests.apply {
            adapter=lapTestsAdapter
            layoutManager=LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL,false)
        }
    }

    private fun showProgress(show:Boolean)
    {
        binding.myprogressLaptests.isVisible=show
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    override fun itemupLapTestsClick(lapTests:MutableList<LapTests>) {
        binding.tvCountLaptests.text="Lap tests cheaked : ${lapTests.size}"
        listLapTests= ListLapTests(lapTests)
    }

}