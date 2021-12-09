package com.example.myassayment.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myassayment.data.remote.FirebaseSource
import com.example.myassayment.models.Client
import com.example.myassayment.utils.StatusResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    val firebase:FirebaseSource
): ViewModel() {

    var _isLogIn:MutableLiveData<StatusResult<Boolean>> = MutableLiveData()
    var _isRegister:MutableLiveData<StatusResult<Boolean>> = MutableLiveData()

    val isLogIn:LiveData<StatusResult<Boolean>> = _isLogIn
    val isRegister:LiveData<StatusResult<Boolean>> = _isRegister
    val currntuser=firebase.user
    val auth=firebase.auth

    fun logIngoogle()=firebase.loginGoogle()

    fun logIn(email:String,pass:String)
    {
        _isLogIn.value=StatusResult.OnLoading()
        firebase.login(email = email,password = pass).addOnCompleteListener {
            if (it.isSuccessful)
            {
                _isLogIn.value=StatusResult.OnSuccess(true)
            }else{
                _isLogIn.value=StatusResult.OnError("${it.exception?.message}")
            }
        }
    }

    fun register(email: String,pass: String,client: Client){
        _isRegister.value=StatusResult.OnLoading()
        if (true)
        {
            firebase.createUsre(email = email,password = pass).addOnCompleteListener {
                if (it.isSuccessful)
                {
                    saveUser(client)
                }else{
                    _isRegister.value=StatusResult.OnError("${it.exception?.message}")
                }
            }
        }else{
            _isRegister.value=StatusResult.OnError("No Internet Connection")
        }

    }

    fun saveUser(client: Client){
        firebase.saveUser(client).addOnCompleteListener {
            if (it.isSuccessful)
            {
                _isRegister.value=StatusResult.OnSuccess(true)
            }else{
                _isRegister.value=StatusResult.OnError("${it.exception?.message}")
            }
        }
    }

}