package com.example.myassayment.ui.body.serviesesfragment

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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myassayment.R
import com.example.myassayment.adapters.BranchItemAdapter
import com.example.myassayment.databinding.FragmentChoseLapBinding
import com.example.myassayment.databinding.FragmentCovid19Binding
import com.example.myassayment.models.BookTest
import com.example.myassayment.models.Branche
import com.example.myassayment.utils.StatusResult
import com.example.myassayment.viewmodels.ServicesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ChoseLapFragment : Fragment(),BranchItemAdapter.BranchItemClick {
    private var _binding: FragmentChoseLapBinding?=null
    private val binding get() = _binding!!
    private val servicesViewModel by viewModels<ServicesViewModel>()
    private val brancheAdapter by lazy { BranchItemAdapter(this,requireContext()) }
    private val navArgs by navArgs<ChoseLapFragmentArgs>()
    private lateinit var bookTest:BookTest
    private var branchChosed:Branche?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentChoseLapBinding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    private fun setUp() {
        bookTest=navArgs.bookTest
        setUpView()
        setupRecy()
        servicesViewModel.getAllBranches(bookTest.area!!,bookTest.city!!)
        setUpObservers()
    }

    private fun setUpView() {
        binding.imgCloseChoselap.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.fabConfirmChoseLap.setOnClickListener {
            val myLasyBook=bookTest
            myLasyBook.apply {
                branchId=branchChosed?.id
                branchName=branchChosed?.name
            }
            confirmBookLapTest(myLasyBook)
        }
    }

    private fun confirmBookLapTest(myLasyBook: BookTest) {
        servicesViewModel.bookLapTest(myLasyBook)
        
    }

    private fun setUpObservers() {
        lifecycleScope.launchWhenStarted {
            servicesViewModel.allBranches.collect {
                when{
                    it is StatusResult.OnSuccess ->{
                        brancheAdapter.setData(it.data!!)
                        binding.progressBar3.isVisible=false
                    }
                    it is StatusResult.OnError ->{
                        Toast.makeText(requireContext(), "${it.msg}", Toast.LENGTH_SHORT).show()
                        binding.progressBar3.isVisible=false
                    }
                    it is StatusResult.OnLoading ->{
                        binding.progressBar3.isVisible=true
                    }
                }
            }
        }
    }

    private fun setupRecy() {
        binding.recyChoselap.apply {
            adapter=brancheAdapter
            layoutManager=LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL,false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    override fun itemClick(branche: Branche) {
        branchChosed=branche
    }
}