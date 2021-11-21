package com.example.myassayment.viewmodels

import androidx.lifecycle.ViewModel
import com.example.myassayment.data.remote.FirebaseSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    val firebase:FirebaseSource
): ViewModel() {
    val currntuser=firebase.user
    val auth=firebase.auth
    fun logIngoogle()=firebase.loginGoogle()


}