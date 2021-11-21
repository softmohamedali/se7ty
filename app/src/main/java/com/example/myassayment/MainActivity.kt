    package com.example.myassayment

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
import com.example.myassayment.databinding.ActivityMainBinding
import com.example.myassayment.utils.Constants
import com.example.myassayment.viewmodels.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint

    @AndroidEntryPoint
class MainActivity : AppCompatActivity(),NavController.OnDestinationChangedListener {
    private var _binding:ActivityMainBinding?=null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()

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

        binding.imgMassageMain.setOnClickListener {
            navController.navigate(R.id.bottomChatFragment)
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Constants.RC_SING_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            if (task.isSuccessful)
            {
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                }
            }
            else
            {
                Log.d("fds",task.exception?.message.toString())
            }

        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        authViewModel.auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                } else {

                }
            }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}