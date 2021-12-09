package com.example.myassayment.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myassayment.data.remote.FirebaseSource
import com.example.myassayment.models.Doctor
import com.example.myassayment.models.Speitality
import com.example.myassayment.utils.MyUtils
import com.example.myassayment.utils.StatusResult
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class DoctorAppointmentViewModel @Inject constructor(
    var firebaseSource: FirebaseSource,
    application: Application
) :AndroidViewModel(application) {
    private var _speialits: MutableLiveData<StatusResult<MutableList<Speitality>>> = MutableLiveData()
    private var _doctorWSpetitlity: MutableLiveData<StatusResult<MutableList<Doctor>>> = MutableLiveData()
    private var _doctorSearchByName: MutableLiveData<StatusResult<MutableList<Doctor>>> = MutableLiveData()

    val speialits: LiveData<StatusResult<MutableList<Speitality>>> = _speialits
    val doctorWSpetitlity: LiveData<StatusResult<MutableList<Doctor>>> = _doctorWSpetitlity
    val doctorSearchByName: LiveData<StatusResult<MutableList<Doctor>>> = _doctorSearchByName

    fun getSpeialits(){
        _speialits.value=StatusResult.OnLoading()
        if (hasInternetConnection())
        {
            firebaseSource.getSpeiality().addSnapshotListener { value, error ->
                if (error==null){
                    _speialits.value=MyUtils.handledata(value)
                }else{
                    _speialits.value=StatusResult.OnError("${error.message}")
                }
            }
        }else{
            _speialits.value=StatusResult.OnError("No Internet Connection")
        }
    }
     fun getDoctorWithSpetility(spetitlity:String){
        _doctorWSpetitlity.value=StatusResult.OnLoading()
        if (hasInternetConnection())
        {
            firebaseSource.getDoctorsWithSpetilst(spetitlity).addSnapshotListener { value, error ->
                if (error==null){
                    _doctorWSpetitlity.value=MyUtils.handledata(value)
                }else{
                    _doctorWSpetitlity.value=StatusResult.OnError("${error.message}")
                }
            }
        }else{
            _doctorWSpetitlity.value=StatusResult.OnError("No Internet Connection")
        }
    }

     fun getDoctorSearchByName(name:String){
        _doctorSearchByName.value=StatusResult.OnLoading()
        if (hasInternetConnection())
        {
            firebaseSource.getDoctorSearchByName(name).addSnapshotListener { value, error ->
                if (error==null){
                    _doctorSearchByName.value=MyUtils.handledata(value)
                }else{
                    _doctorSearchByName.value=StatusResult.OnError("${error.message}")
                }
            }
        }else{
            _doctorSearchByName.value=StatusResult.OnError("No Internet Connection")
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