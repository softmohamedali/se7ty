package com.example.myassayment.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.myassayment.R
import com.example.myassayment.databinding.ActivityMainBinding
import com.example.myassayment.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()
    private lateinit var navController: NavController
    private val ACCEPT_PAYMENT_REQUEST=10
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.fragmentContainerView)
        binding.bottomNavigationView.setupWithNavController(navController)
        var navhostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        var mynavController = navhostFragment.navController
        mynavController.addOnDestinationChangedListener(this)

        binding.imgMassageMain.setOnClickListener {
            navController.navigate(R.id.bottomChatFragment)
        }
        binding.imgMyAccountMain.setOnClickListener {
            if (authViewModel.currntuser()==null)
            {
                navController.navigate(R.id.action_to_loginFragment)
            }
            else{
                navController.navigate(R.id.action_to_accountFragment)
            }
        }

    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.mainFragment ->{
                binding.bottomNavigationView.isVisible=true
                binding.containerBarMain.isVisible=true
                binding.tvLabelMain.text="se7ty"
            }
            R.id.bookingFragment ->{
                binding.bottomNavigationView.isVisible=true
                binding.containerBarMain.isVisible=true
                binding.tvLabelMain.text="Booking"
            }
            R.id.servicesFragment ->{
                binding.bottomNavigationView.isVisible=true
                binding.containerBarMain.isVisible=true
                binding.tvLabelMain.text="Services"
            }
            R.id.emergencyFragment ->{
                binding.bottomNavigationView.isVisible=true
                binding.containerBarMain.isVisible=true
                binding.tvLabelMain.text="Emergency"
            }
            R.id.bottomChatFragment ->{
                binding.bottomNavigationView.isVisible=true
                binding.containerBarMain.isVisible=true
                binding.tvLabelMain.text="se7ty"
            }
            R.id.locatonsFragment->{
                binding.bottomNavigationView.isVisible=true
                binding.containerBarMain.isVisible=true
                binding.tvLabelMain.text="se7ty"
            }
            R.id.accountFragment ->{
                binding.bottomNavigationView.isVisible=true
                binding.containerBarMain.isVisible=true
                binding.tvLabelMain.text="se7ty"
            }
            else -> {
                binding.bottomNavigationView.isVisible=false
                binding.containerBarMain.isVisible=false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}