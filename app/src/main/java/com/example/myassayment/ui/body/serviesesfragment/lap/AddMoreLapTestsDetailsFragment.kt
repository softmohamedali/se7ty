package com.example.myassayment.ui.body.serviesesfragment.lap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myassayment.R
import com.example.myassayment.databinding.FragmentAddMoreLapTestsDetailsBinding
import com.example.myassayment.helpers.Validation
import com.example.myassayment.models.BookTest
import com.example.myassayment.models.LapTests
import com.example.myassayment.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddMoreLapTestsDetailsFragment : Fragment() {
    private var _binding: FragmentAddMoreLapTestsDetailsBinding?=null
    private val binding get() = _binding!!
    private val navArgs by navArgs<AddMoreLapTestsDetailsFragmentArgs>()
    private lateinit var mlaptests:MutableList<LapTests>
    private var mforWho=Constants.FOR_ME
    private var mcity:String?=null
    private var marea:String?=null
    private var mlocation:String=Constants.FROM_HOME

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentAddMoreLapTestsDetailsBinding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    private fun setUp() {
        setUpview()
        mlaptests=navArgs.cheakedLaptests.list!!
    }



    fun setUpview() {
        binding.etPatientadressLapmore.isVisible=true
        binding.tvAddressAddmore.isVisible=true
        binding.btnMyselfLapmore.setOnClickListener {
            changeViewForWhoStatus(Constants.FOR_ME)
        }
        binding.btnOthersLapmore.setOnClickListener {
            changeViewForWhoStatus(Constants.FOR_OTHERS)
        }
        binding.btnAthomeLapmore.setOnClickListener {
            mlocation = Constants.FROM_HOME
            changeViewlocationStatus(Constants.FROM_HOME)
        }
        binding.btnAtrancheLapmore.setOnClickListener {
            mlocation = Constants.FROM_BRANCH
            changeViewlocationStatus(Constants.FROM_BRANCH)
        }
        binding.fabAddmorelap.setOnClickListener {
            saveData()
        }
        binding.imgCloseAddlaptests.setOnClickListener {
            findNavController().popBackStack()
        }
        val arrayCity = arrayOf("fd", "sdf", "jky")
        mcity = arrayCity[0]
        marea = arrayCity[0]
        val adapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, arrayCity)
        binding.spAreaLapmore.adapter = adapter
        binding.spCityLapmore.adapter = adapter
        binding.spCityLapmore.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                mcity = p0?.getItemAtPosition(p2).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        binding.spAreaLapmore.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                marea = p0?.getItemAtPosition(p2).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    private fun saveData() {
        val patientName=binding.etPtientnameLapmore.text.toString()
        val patientPhhone=binding.etPhoneLapmore.text.toString()
        val patientAddress=binding.etPatientadressLapmore.text.toString()
        val patientLocation=mlocation
        val forWho=mforWho

        if(Validation.isFeildIsEmpty(binding.etPatientadressLapmore)||
            Validation.isFeildIsEmpty(binding.etPhoneLapmore)||
            Validation.isFeildIsEmpty(binding.etPtientnameLapmore)||
            mcity.isNullOrEmpty()||
            marea.isNullOrEmpty()||
            patientLocation.trim().isEmpty()||
            forWho.trim().isEmpty()) {
            return
        }
        val bookTests=BookTest(
            patientName = patientName,
            patientAddres = patientAddress,
            patientPhone = patientPhhone,
            city = mcity,
            area = marea,
            location = patientLocation,
            forWho = forWho,
            lapTests = mlaptests
        )
        val action=AddMoreLapTestsDetailsFragmentDirections
            .actionAddMoreLapTestsDetailsFragmentToChoseLapFragment(bookTests)
        findNavController().navigate(action)

    }

    private fun changeViewForWhoStatus(forwho: String) {
        val mainColor=ContextCompat.getColor(requireActivity(), R.color.background_tool)
        val secColor=ContextCompat.getColor(requireActivity(), R.color.white)
        mforWho=forwho
        if (forwho==Constants.FOR_ME)
        {
            binding.btnMyselfLapmore.setBackgroundColor(mainColor)
            binding.btnOthersLapmore.setBackgroundColor(secColor)
            binding.btnMyselfLapmore.setTextColor(secColor)
            binding.btnOthersLapmore.setTextColor(mainColor)
        }else{
            binding.btnOthersLapmore.setBackgroundColor(mainColor)
            binding.btnMyselfLapmore.setBackgroundColor(secColor)
            binding.btnOthersLapmore.setTextColor(secColor)
            binding.btnMyselfLapmore.setTextColor(mainColor)
        }
    }

    private fun changeViewlocationStatus(location: String) {
        mlocation=location
        val mainColor=ContextCompat.getColor(requireActivity(), R.color.background_tool)
        val secColor=ContextCompat.getColor(requireActivity(), R.color.white)
        if (location==Constants.FROM_HOME)
        {
            binding.athomeLapmore.setBackgroundColor(mainColor)
            binding.atrancheLapmore.setBackgroundColor(secColor)
            binding.tvHomeMorelap.setTextColor(secColor)
            binding.tvBranchMorelap.setTextColor(mainColor)
            binding.icHomeMorelap.setColorFilter(secColor)
            binding.icBranchMorelap.setColorFilter(mainColor)
            binding.etPatientadressLapmore.isVisible=true
            binding.tvAddressAddmore.isVisible=true
        }else{
            binding.athomeLapmore.setBackgroundColor(secColor)
            binding.atrancheLapmore.setBackgroundColor(mainColor)
            binding.tvBranchMorelap.setTextColor(secColor)
            binding.tvHomeMorelap.setTextColor(mainColor)
            binding.icBranchMorelap.setColorFilter(secColor)
            binding.icHomeMorelap.setColorFilter(mainColor)
            binding.etPatientadressLapmore.isVisible=false
            binding.tvAddressAddmore.isVisible=false
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}