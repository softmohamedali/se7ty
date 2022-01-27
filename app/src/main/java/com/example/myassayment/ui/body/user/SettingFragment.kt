package com.example.myassayment.ui.body.user

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.example.myassayment.R
import com.example.myassayment.databinding.FragmentPreviousBookingBinding
import com.example.myassayment.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import android.os.Build




@AndroidEntryPoint
class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    lateinit var shared: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(layoutInflater)
        shared = requireContext().getSharedPreferences("lang", Context.MODE_PRIVATE)
        setUp()
        return binding.root
    }

    private fun setUp() {
        if (getLangSha() == "en") {
            binding.tvLang.text = "English"
        } else {
            binding.tvLang.text = "العربيه"
        }
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnChangLang.setOnClickListener {
            changLang()
        }
        binding.btnChangPass.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_changePasswordFragment)
        }
    }

    private fun changLang() {
        if (getLangSha() == "en") {
            changeLang("ar")
            saveShared("ar")
            binding.tvLang.text = "العربيه"

        } else {
            changeLang("en")
            saveShared("en")
            binding.tvLang.text = "English"
        }

        requireActivity().recreate()
    }


    private fun changeLang(localcode: String) {
        val local = Locale(localcode)
        Locale.setDefault(local)
        val resourses = requireActivity().resources
        val config = resourses.configuration
        config.setLocale(local)
        resourses.updateConfiguration(config, resourses.displayMetrics)
    }

    fun saveShared(lang: String) {
        val editor = shared.edit()
        editor.apply {
            putString("lang", lang)
            apply()
        }
    }

    fun getLangSha(): String {
        val lang = shared.getString("lang", "en")
        return lang!!
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}