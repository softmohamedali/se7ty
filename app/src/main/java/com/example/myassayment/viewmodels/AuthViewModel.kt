package com.example.myassayment.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myassayment.data.remote.FirebaseSource
import com.example.myassayment.models.Client
import com.example.myassayment.models.Medecation
import com.example.myassayment.models.MedicalTest
import com.example.myassayment.utils.MyUtils
import com.example.myassayment.utils.MyUtils.handledata
import com.example.myassayment.utils.StatusResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    private var _isAddtestUser:MutableLiveData<StatusResult<Boolean>> = MutableLiveData()
    private var _isAddMedicationUser:MutableLiveData<StatusResult<Boolean>> = MutableLiveData()
    private var _medicalTestUser:MutableStateFlow<StatusResult<MutableList<MedicalTest>>?> =
        MutableStateFlow(null)
    private var _medicationUser:MutableStateFlow<StatusResult<MutableList<Medecation>>?> =
        MutableStateFlow(null)
    private var _isDelteMedicalTest:MutableLiveData<StatusResult<Boolean>> = MutableLiveData()
    private var _isDelteMedication:MutableLiveData<StatusResult<Boolean>> = MutableLiveData()
    private var _isChangePass:MutableLiveData<StatusResult<Boolean>> = MutableLiveData()

    val isLogIn:LiveData<StatusResult<Boolean>> = _isLogIn
    val isRegister:LiveData<StatusResult<Boolean>> = _isRegister
    val isPassSend:LiveData<StatusResult<Boolean>> = _isPassSend
    val isgetUser:LiveData<StatusResult<Client>> = _isgetUser
    val isSave:LiveData<StatusResult<Boolean>> = _isSave
    val isAddtestUser:LiveData<StatusResult<Boolean>> = _isAddtestUser
    val isAddMedicationUser:LiveData<StatusResult<Boolean>> = _isAddMedicationUser
    val medicalTestUser:StateFlow<StatusResult<MutableList<MedicalTest>>?> = _medicalTestUser
    val medicationUser:StateFlow<StatusResult<MutableList<Medecation>>?> = _medicationUser

    val isDelteMedicalTest:LiveData<StatusResult<Boolean>> = _isDelteMedicalTest
    val isDelteMedication:LiveData<StatusResult<Boolean>> = _isDelteMedication
    val isChangePass:LiveData<StatusResult<Boolean>> = _isChangePass

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
                    saveUser(client,null)
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
                    saveUser(client,null)
                }else{
                    _isRegister.value=StatusResult.OnError("${it.exception?.message}")
                }
            }
        }else{
            _isRegister.value=StatusResult.OnError("No Internet Connection")
        }

    }

    fun changePassword(curretPass:String,newPass:String)
    {
        _isChangePass.value=StatusResult.OnLoading()
        if (hasInternetConnection())
        {
            firebase.changePassword(currePass = curretPass,newPass = curretPass).addOnCompleteListener {
                if (it.isSuccessful) {
                    firebase.user()!!.updatePassword(newPass).addOnCompleteListener {
                        if (it.isSuccessful){
                            _isChangePass.value = StatusResult.OnSuccess(true)
                        }else{
                            _isChangePass.value = StatusResult.OnError("${it.exception?.message}")
                        }
                    }
                } else {
                    _isChangePass.value = StatusResult.OnError("${it.exception?.message}")
                }
            }
        }else{
            _isChangePass.value = StatusResult.OnError("No Internet Connection")
        }
    }

    fun saveUser(client: Client,imgUser:ByteArray?){
        _isSave.value=StatusResult.OnLoading()
        if (hasInternetConnection())
        {
            if (imgUser!=null)
            {
                firebase.saveImg(imgUser) {
                    client.img = it
                    client.photoUrl=it
                    saveUserObject(client)
                }
            }else{
                saveUserObject(client)
            }
        }else{
            _isRegister.value=StatusResult.OnError("No Internet Connection")
            _isSave.value=StatusResult.OnError("No Internet Connection")
        }
    }

    fun saveUserObject(user:Client){
        firebase.saveUser(user).addOnCompleteListener {
            if (it.isSuccessful)
            {
                _isRegister.value=StatusResult.OnSuccess(true)
                _isSave.value=StatusResult.OnSuccess(true)
            }else{
                _isRegister.value=StatusResult.OnError("${it.exception?.message}")
                _isSave.value=StatusResult.OnError("${it.exception?.message}")
            }
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


    fun addMedicalTestForUser(test:MedicalTest, imgTest:ByteArray?)
    {
        _isAddtestUser.value=StatusResult.OnLoading()
        if (hasInternetConnection())
        {
            if (imgTest!=null)
            {
                firebase.saveImg(imgTest) {
                    test.img = it
                    saveMedicalTest(test)
                }
            }else{
                saveMedicalTest(test)
            }
        }else{
            _isAddtestUser.value=StatusResult.OnError("No Internet Connection")
        }
    }

    fun saveMedicalTest(medicalTest: MedicalTest)
    {
        firebase.addTestForUser(medicalTest).addOnCompleteListener {
            if (it.isSuccessful)
            {
                _isAddtestUser.value=StatusResult.OnSuccess(true)
            }else{
                _isRegister.value=StatusResult.OnError("${it.exception?.message}")
            }
        }
    }

    fun delteMedicalTest(medicalTest: MedicalTest)
    {
        _isDelteMedicalTest.value=StatusResult.OnLoading()
        if (hasInternetConnection())
        {
            firebase.deleteMedicalTestForUser(medicalTest).addOnCompleteListener {
                if (it.isSuccessful)
                {
                    _isDelteMedicalTest.value=StatusResult.OnSuccess(true)
                }else{
                    _isDelteMedicalTest.value=StatusResult.OnError("${it.exception?.message}")
                }
            }
        }else{
            _isDelteMedicalTest.value=StatusResult.OnError("No Internet Connection")
        }
    }


    fun getUserMedicalTest(){
        if (hasInternetConnection()) {
            _medicalTestUser.value=StatusResult.OnLoading()
            firebase.getUserMedicalTest().addSnapshotListener { value, error ->
                if (error == null) {
                    _medicalTestUser.value=handledata<MedicalTest>(value)
                } else {
                    _medicalTestUser.value=StatusResult.OnError("No internet Connection")
                }
            }
        } else {
            _medicalTestUser.value=StatusResult.OnError("No internet Connection")
        }
    }

    fun addMedicationForUser(medication:Medecation)
    {
        _isAddMedicationUser.value=StatusResult.OnLoading()
        if (hasInternetConnection())
        {
            firebase.addMedicationForUser(medication).addOnCompleteListener {
                if (it.isSuccessful)
                {
                    _isAddMedicationUser.value=StatusResult.OnSuccess(true)
                }else{
                    _isAddMedicationUser.value=StatusResult.OnError("${it.exception?.message}")
                }
            }
        }else{
            _isAddMedicationUser.value=StatusResult.OnError("No Internet Connection")
        }
    }
    fun delteMedication(medication: Medecation)
    {
        _isDelteMedication.value=StatusResult.OnLoading()
        if (hasInternetConnection())
        {
            firebase.deleteMedicationForUser(medication).addOnCompleteListener {
                if (it.isSuccessful)
                {
                    _isDelteMedication.value=StatusResult.OnSuccess(true)
                }else{
                    _isDelteMedication.value=StatusResult.OnError("${it.exception?.message}")
                }
            }
        }else{
            _isDelteMedication.value=StatusResult.OnError("No Internet Connection")
        }
    }
    fun getUserMedication(){
        if (hasInternetConnection()) {
            _medicationUser.value=StatusResult.OnLoading()
            firebase.getUserMedication().addSnapshotListener { value, error ->
                if (error == null) {
                    _medicationUser.value=handledata<Medecation>(value)
                } else {
                    _medicationUser.value=StatusResult.OnError("No internet Connection")
                }
            }
        } else {
            _medicationUser.value=StatusResult.OnError("No internet Connection")
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