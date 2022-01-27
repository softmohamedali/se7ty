package com.example.myassayment.ui.body.user

import android.app.Activity
import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myassayment.R
import com.example.myassayment.databinding.FragmentAddMedicationBinding
import com.example.myassayment.helpers.Validation
import com.example.myassayment.models.Medecation
import com.example.myassayment.utils.StatusResult
import com.example.myassayment.viewmodels.AuthViewModel
import com.example.myassayment.viewmodels.ServicesViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddMedicationFragment : Fragment() {
    private var _binding: FragmentAddMedicationBinding?=null
    private val binding get() = _binding!!
    private val servicesViewModel by viewModels<ServicesViewModel>()
    private val authViewModel by viewModels<AuthViewModel>()
    private var mMedicationNavArgs: Medecation?=null
    private var mMedication: Medecation = Medecation()
    private val navArgs by navArgs<AddMedicationFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentAddMedicationBinding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    private fun setUp() {
        mMedicationNavArgs=navArgs.medication
        setUpObserver()
        setUpView()
        binding.btnSave.setOnClickListener {
            saveMedication()
        }
        binding.btnCancle.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnDelete.setOnClickListener {
            delteMedicalTest()
        }
    }

    private fun delteMedicalTest() {
        authViewModel.delteMedication(medication = mMedication)
    }

    private fun saveMedication() {
        val medication=binding.etMedicationName.text.toString()
        val additionalNote=binding.etNoteAdditional.text.toString()
        if (
            Validation.isFeildIsEmpty(binding.etMedicationName)||
            Validation.isFeildIsEmpty(binding.etMedicationName)
        ){
            return
        }else{
            mMedication.medicationName = medication
            mMedication.notes = additionalNote
            mMedication.chronicDrug=binding.swDrug.isChecked.toString()
            authViewModel.addMedicationForUser(mMedication)
        }
    }

    private fun setUpView() {
        if (mMedicationNavArgs!=null){
            mMedication=mMedicationNavArgs!!
            showEditStuff(true)
            setMedicationStuff(mMedicationNavArgs!!)
        }else{
            showEditStuff(false)
        }
        servicesViewModel.getAllLapTests()
        servicesViewModel.getAllBranches()
    }

    private fun setMedicationStuff(medication: Medecation) {
        binding.etMedicationName.setText(medication.medicationName)
        binding.etNoteAdditional.setText(medication.notes)
        binding.swDrug.isChecked=medication.chronicDrug.toBoolean()
    }

    private fun showEditStuff(show: Boolean) {
        if (show){
            binding.btnDelete.visibility=View.VISIBLE
        }else{
            binding.btnDelete.visibility=View.GONE
        }
    }

    private fun setUpObserver() {
        authViewModel.isAddMedicationUser.observe(viewLifecycleOwner,{
            when{
                it is StatusResult.OnError ->{
                    Toast.makeText(requireContext(), "${it.msg}", Toast.LENGTH_SHORT).show()
                    showLoading(false)
                }
                it is StatusResult.OnLoading -> {
                    showLoading(true)
                }
                it is StatusResult.OnSuccess ->{
                    showLoading(false)
                    Snackbar.make(binding.root,"Success Add Medical Test", Snackbar.LENGTH_SHORT)
                        .setAction("OK",{}).show()
                }
            }
        })
        authViewModel.isDelteMedication.observe(viewLifecycleOwner,{
            when{
                it is StatusResult.OnError ->{
                    Toast.makeText(requireContext(), "${it.msg}", Toast.LENGTH_SHORT).show()
                    showLoading(false)
                }
                it is StatusResult.OnLoading -> {
                    showLoading(true)
                }
                it is StatusResult.OnSuccess ->{
                    showLoading(false)
                    Snackbar.make(binding.root,"Success Delete Medical Test", Snackbar.LENGTH_SHORT)
                        .setAction("OK",{}).show()
                    findNavController().popBackStack()
                }
            }
        })
    }

    private fun showLoading(show:Boolean)
    {
        if (show){
            binding.pbMedication.isVisible=true
            binding.viewAddMedication.setBackgroundColor(
                ContextCompat
                    .getColor(requireContext(), R.color.blackop))
            binding.containerAddMedicalTest.isFocusable=false
        }else{
            binding.pbMedication.isVisible=false
            binding.viewAddMedication.setBackgroundColor(
                ContextCompat
                    .getColor(requireContext(), R.color.none))
            binding.containerAddMedicalTest.isFocusable=true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}