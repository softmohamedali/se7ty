    package com.example.myassayment

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.myassayment.databinding.ActivityMainBinding
import com.example.myassayment.ui.BottomChatFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),NavController.OnDestinationChangedListener {
    private var _binding:ActivityMainBinding?=null
    private val binding get() = _binding!!

    private lateinit var navController:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController=findNavController(R.id.fragmentContainerView)
        binding.bottomNavigationView.setupWithNavController(navController)
        var navhostFragment=supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        var mynavController=navhostFragment.navController
        mynavController.addOnDestinationChangedListener(this)

        binding.imageButton.setOnClickListener {
            navController.navigate(R.id.bottomChatFragment)
        }
//        BottomSheetBehavior.from(binding.root).peekHeight = Resources.getSystem().getDisplayMetrics().heightPixels
//        BottomSheetBehavior.from(binding.root).state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when(destination.id)
        {
            R.id.mainFragment,R.id.bookingFragment,
            R.id.servicesFragment,R.id.emergencyFragment,R.id.bottomChatFragment ->{
                binding.constraintLayout.isVisible=true
            }
            else ->{
                binding.constraintLayout.isVisible=false
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}