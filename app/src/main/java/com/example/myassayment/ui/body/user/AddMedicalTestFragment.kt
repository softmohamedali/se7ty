package com.example.myassayment.ui.body.user

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.myassayment.R
import com.example.myassayment.databinding.FragmentAddMedicalTestBinding
import com.example.myassayment.helpers.Validation
import com.example.myassayment.models.MedicalTest
import com.example.myassayment.utils.StatusResult
import com.example.myassayment.viewmodels.AuthViewModel
import com.example.myassayment.viewmodels.ServicesViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddMedicalTestFragment : Fragment() {
    private var _binding: FragmentAddMedicalTestBinding?=null
    private val binding get() = _binding!!
    private val servicesViewModel by viewModels<ServicesViewModel>()
    private val authViewModel by viewModels<AuthViewModel>()
    private var imgtest:ByteArray?=null
    private var mMedicaltestNavArgs:MedicalTest?=null
    private var mMedicaltest:MedicalTest= MedicalTest()
    private val navArgs by navArgs<AddMedicalTestFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentAddMedicalTestBinding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    private fun setUp() {
        mMedicaltestNavArgs=navArgs.medicalTest
        setUpObserver()
        setUpView()
        binding.btnSave.setOnClickListener {
            saveMedicalTest()
        }
        binding.btnClose.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnSelectImg.setOnClickListener {
            selectImg()
        }
        binding.etDate.setOnClickListener {
            showDateDialog()
        }
        binding.imgCloseImg.setOnClickListener {
            delteImgTest()
        }
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.tvDelete.setOnClickListener {
            delteMedicalTest()
        }
    }

    private fun delteMedicalTest() {
        authViewModel.delteMedicalTest(medicalTest = mMedicaltest)
    }

    private fun saveMedicalTest() {
        val labName=binding.mnueLapName.text.toString()
        val testName=binding.mnueTestName.text.toString()
        val dateTest=binding.etDate.text.toString()
        val resultTest=binding.etResult.text.toString()
        if (
            Validation.isFeildIsEmpty(binding.mnueLapName)||
            Validation.isFeildIsEmpty(binding.mnueTestName)||
            Validation.isFeildIsEmpty(binding.etDate)||
            Validation.isFeildIsEmpty(binding.etResult)
        ){
            return
        }else{
            mMedicaltest.labName = labName
            mMedicaltest.testdate = dateTest
            mMedicaltest.testName = testName
            mMedicaltest.testResult = resultTest
            authViewModel.addMedicalTestForUser(mMedicaltest,imgtest)
        }
    }


    private fun selectImg() {
        val intent=Intent().apply {
            action=Intent.ACTION_GET_CONTENT
            type="image/*"
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/png","image/jpeg"))
        }
        registerLaunche.launch(intent)
    }

    private fun setUpView() {
        if (mMedicaltestNavArgs!=null){
            mMedicaltest=mMedicaltestNavArgs!!
            showEditStuff(true)
            setMedicalTestStuff(mMedicaltestNavArgs!!)
        }else{
            showEditStuff(false)
        }
        servicesViewModel.getAllLapTests()
        servicesViewModel.getAllBranches()
    }

    private fun setMedicalTestStuff(medicaltest: MedicalTest) {
        binding.etDate.setText(medicaltest.testdate)
        binding.etResult.setText(medicaltest.testResult)
        binding.mnueTestName.setText(medicaltest.testName)
        binding.mnueLapName.setText(medicaltest.labName)
        if(!medicaltest.img.isNullOrEmpty())
        {
            showImgTest(true)
            binding.imgTest.load(medicaltest.img)
        }else{
            showImgTest(false)
        }
    }

    private fun showEditStuff(show: Boolean) {
        if (show){
            binding.tvDelete.visibility=View.VISIBLE
        }else{
            binding.tvDelete.visibility=View.GONE
        }
    }

    private fun setUpObserver() {
        lifecycleScope.launchWhenStarted {
            servicesViewModel.allBranches.collect{
                when{
                    it is StatusResult.OnError ->{
                        Toast.makeText(requireContext(), "${it.msg}", Toast.LENGTH_SHORT).show()
                    }
                    it is StatusResult.OnLoading -> {

                    }
                    it is StatusResult.OnSuccess ->{
                        val branches=it.data!!.map { it.name }
                        val adapterView=ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,branches)
                        binding.mnueLapName.setAdapter(adapterView)
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            servicesViewModel.lapTests.collect{
                when{
                    it is StatusResult.OnError ->{
                        Toast.makeText(requireContext(), "${it.msg}", Toast.LENGTH_SHORT).show()
                    }
                    it is StatusResult.OnLoading -> {

                    }
                    it is StatusResult.OnSuccess ->{
                        val tests=it.data!!.map { it.name }
                        val adapterView=ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,tests)
                        binding.mnueTestName.setAdapter(adapterView)
                    }
                }
            }
        }

        authViewModel.isAddtestUser.observe(viewLifecycleOwner,{
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
                    Snackbar.make(binding.root,"Success Add Medical Test",Snackbar.LENGTH_SHORT)
                        .setAction("OK",{}).show()
                }
            }
        })
        authViewModel.isDelteMedicalTest.observe(viewLifecycleOwner,{
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
                    Snackbar.make(binding.root,"Success Delete Medical Test",Snackbar.LENGTH_SHORT)
                        .setAction("OK",{}).show()
                    findNavController().popBackStack()
                }
            }
        })
    }

    private fun showDateDialog() {

        val calender = Calendar.getInstance()
        val datePiker= DatePickerDialog.OnDateSetListener { datePicker, y, m, d ->
            calender.set(Calendar.YEAR,y)
            calender.set(Calendar.MONTH,m)
            calender.set(Calendar.DAY_OF_MONTH,d)
            val format="dd-MM-yyyy"
            val simpleDateFormat=SimpleDateFormat(format,Locale.UK)
            binding.etDate.setText(simpleDateFormat.format(calender.time))
        }
        DatePickerDialog(requireActivity(),datePiker,calender.get(Calendar.YEAR)
            ,calender.get(Calendar.MONTH),calender.get(Calendar.DAY_OF_MONTH)).show()
    }

    val registerLaunche=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.data!=null&&it.resultCode==Activity.RESULT_OK){
            val imgUrl=it.data!!.data
            val bitmap=MediaStore.Images.Media.getBitmap(requireActivity().contentResolver,imgUrl)
            val outByteArray=ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG,25,outByteArray)
            val outByteArra=outByteArray.toByteArray()
            imgtest=outByteArra
            binding.imgTest.setImageURI(imgUrl)
            showImgTest(true)
        }
    }

    fun showImgTest(show:Boolean)
    {
        if (show)
        {
            binding.imgTest.visibility=View.VISIBLE
            binding.imgCloseImg.visibility=View.VISIBLE

        }else{
            binding.imgTest.visibility=View.GONE
            binding.imgCloseImg.visibility=View.GONE
        }
    }

    private fun delteImgTest() {
        imgtest=null
        mMedicaltest.img=""
        showImgTest(false)
    }

    private fun showLoading(show:Boolean)
    {
        if (show){
            binding.pbAddtest.isVisible=true
            binding.viewAddMedicalTest.setBackgroundColor(
                ContextCompat
                .getColor(requireContext(), R.color.blackop))
            binding.containerAddMedicalTest.isFocusable=false
        }else{
            binding.pbAddtest.isVisible=false
            binding.viewAddMedicalTest.setBackgroundColor(
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