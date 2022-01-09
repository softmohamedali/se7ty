package com.example.myassayment.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.myassayment.data.remote.FirebaseSource
import com.example.myassayment.models.Client
import com.example.myassayment.utils.MyUtils
import com.example.myassayment.utils.StatusResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    val firebase:FirebaseSource,
    application: Application
): AndroidViewModel(application) {

    private var _isLogIn:MutableLiveData<StatusResult<Boolean>> = MutableLiveData()
    private var _isRegister:MutableLiveData<StatusResult<Boolean>> = MutableLiveData()
    private var _isPassSend:MutableLiveData<StatusResult<Boolean>> = MutableLiveData()
    private var _isgetUser:MutableLiveData<StatusResult<Client>> = MutableLiveData()
    private var _isSave:MutableLiveData<StatusResult<Boolean>> = MutableLiveData()

    val isLogIn:LiveData<StatusResult<Boolean>> = _isLogIn
    val isRegister:LiveData<StatusResult<Boolean>> = _isRegister
    val isPassSend:LiveData<StatusResult<Boolean>> = _isPassSend
    val isgetUser:LiveData<StatusResult<Client>> = _isgetUser
    val isSave:LiveData<StatusResult<Boolean>> = _isSave
    fun currntuser()=firebase.user()
    val auth=firebase.auth

    fun logIngoogle()=firebase.loginGoogle()

    fun firebaseSinginWithGoogle(idToken:String){
        _isRegister.value=StatusResult.OnLoading()
        if (hasInternetConnection())
        {
            firebase.firebaseSinginWithGoogle(idToken).addOnCompleteListener {
                if (it.isSuccessful) {
                    val client=Client(
                        name = it.result.user?.displayName,
                        email = it.result.user?.email,
                        phone = it.result.user?.phoneNumber,
                        photoUrl = it.result.user?.photoUrl.toString()
                    )
                    saveUser(client)
                } else {
                    _isRegister.value=StatusResult.OnError("${it.exception?.message}")
                }
            }
        }else{
            _isRegister.value=StatusResult.OnError("No Internet Connection")
        }
    }

    fun logOut()=firebase.logout()
    fun logIn(email:String,pass:String)
    {
        _isLogIn.value=StatusResult.OnLoading()
        if (hasInternetConnection())
        {
            firebase.login(email = email,password = pass).addOnCompleteListener {
                if (it.isSuccessful) {
                    _isLogIn.value = StatusResult.OnSuccess(true)
                } else {
                    _isLogIn.value = StatusResult.OnError("${it.exception?.message}")
                }
            }
        }else{
            _isLogIn.value = StatusResult.OnError("No Internet Connection")
        }
    }

    fun register(email: String,pass: String,client: Client){
        _isRegister.value=StatusResult.OnLoading()
        if (hasInternetConnection())
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
        _isSave.value=StatusResult.OnLoading()
        if (hasInternetConnection())
        {
            firebase.saveUser(client).addOnCompleteListener {
                if (it.isSuccessful)
                {
                    _isRegister.value=StatusResult.OnSuccess(true)
                    _isSave.value=StatusResult.OnSuccess(true)
                }else{
                    _isRegister.value=StatusResult.OnError("${it.exception?.message}")
                    _isSave.value=StatusResult.OnError("${it.exception?.message}")
                }
            }
        }else{
            _isRegister.value=StatusResult.OnError("No Internet Connection")
            _isSave.value=StatusResult.OnError("No Internet Connection")
        }
    }

    fun findMyPassword(email:String)
    {
        _isPassSend.value=StatusResult.OnLoading()
        if (hasInternetConnection())
        {
            firebase.resetPassword(email).addOnCompleteListener {
                if (it.isSuccessful)
                {
                    _isPassSend.value=StatusResult.OnSuccess(true)
                }else{
                    _isPassSend.value=StatusResult.OnError("${it.exception?.message}")
                }
            }
        }else{
            _isPassSend.value=StatusResult.OnError("No Internet Connection")
        }
    }

    fun getUserInfo(){
        _isgetUser.value=StatusResult.OnLoading()
        if (hasInternetConnection())
        {
            firebase.getUserInfo().addOnCompleteListener {
                if (it.isSuccessful)
                {
                    _isgetUser.value=MyUtils.handleSingledata(it)
                }else{
                    _isgetUser.value=StatusResult.OnError("${it.exception?.message}")
                }
            }
        }else{
            _isgetUser.value=StatusResult.OnError("No Internet Connection")
        }
    }




    private fun hasInternetConnection(): Boolean {
        val connectivityManger = getApplication<Application>()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netWorkActive = connectivityManger.activeNetwork ?: return false
        val networkCapability =
            connectivityManger.getNetworkCapabilities(netWorkActive) ?: return false
        when {
            networkCapability.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
            networkCapability.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            networkCapability.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
            else -> return false
        }

    }

}