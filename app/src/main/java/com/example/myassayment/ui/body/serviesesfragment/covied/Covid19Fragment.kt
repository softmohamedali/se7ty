package com.example.myassayment.ui.body.serviesesfragment.covied

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myassayment.adapters.LapTestsItemAdapter
import com.example.myassayment.databinding.FragmentCovid19Binding
import com.example.myassayment.models.LapTests
import com.example.myassayment.models.ListLapTests
import com.example.myassayment.ui.body.serviesesfragment.lap.LapTestFragmentDirections
import com.example.myassayment.utils.StatusResult
import com.example.myassayment.viewmodels.ServicesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class Covid19Fragment : Fragment(),LapTestsItemAdapter.LapTestsItemClick {
    private var _binding: FragmentCovid19Binding?=null
    private val binding get() = _binding!!
    private val servicesViewModel by viewModels<ServicesViewModel>()
    private val lapTestsAdapter by lazy { LapTestsItemAdapter(this) }
    private lateinit var listLapTests:ListLapTests
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentCovid19Binding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    private fun setUp() {
        binding.tvCountCovied.text="Lap tests cheaked : 0"
        setUpRecy()
        servicesViewModel.getAllCoviedTests()
        setUpObservers()
        binding.fabGotoAddmoreCovied.setOnClickListener {
            if (lapTestsAdapter.getLapTestCheaked().size>0)
            {
                val action= Covid19FragmentDirections
                    .actionCovid19FragmentToAddMoreLapTestsDetailsFragment(listLapTests)
                findNavController().navigate(action)
            }else{
                Toast.makeText(requireContext(), "Please Select Lap Test", Toast.LENGTH_SHORT).show()
            }
        }
        binding.imgCloseLaptests.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun setUpObservers() {
        lifecycleScope.launchWhenStarted {
            servicesViewModel.coviedTests.collect {
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
        binding.recyCovied.apply {
            adapter=lapTestsAdapter
            layoutManager= LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL,false)
        }
    }

    private fun showProgress(show:Boolean)
    {
        binding.myprogressCovied.isVisible=show
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    override fun itemupLapTestsClick(lapTests:MutableList<LapTests>) {
        binding.tvCountCovied.text="Lap tests cheaked : ${lapTests.size}"
        listLapTests= ListLapTests(lapTests)
    }
}