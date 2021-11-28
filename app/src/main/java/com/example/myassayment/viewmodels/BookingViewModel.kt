package com.example.myassayment.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.myassayment.data.remote.FirebaseSource
import com.example.myassayment.models.TimeSchedule
import com.example.myassayment.utils.MyUtils
import com.example.myassayment.utils.StatusResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val firebase:FirebaseSource,
    application: Application
):AndroidViewModel(application) {

    private var _dateScedulaDoctor: MutableLiveData<StatusResult<MutableList<TimeSchedule>>> = MutableLiveData()

    val dateScedulaDoctor: MutableLiveData<StatusResult<MutableList<TimeSchedule>>> =_dateScedulaDoctor

    fun getDoctorSchedula(doctorId:String){
        _dateScedulaDoctor.value=StatusResult.OnLoading()
        if (hasInternetConnection()){
            firebase.getAllDoctorsTimeSchedula(doctorId).addSnapshotListener { value, error ->
                if (error==null)
                {
                    _dateScedulaDoctor.value=MyUtils.handledata(value)
                }else{
                    _dateScedulaDoctor.value=StatusResult.OnError(error.message.toString())
                }
            }
        }else{
            _dateScedulaDoctor.value=StatusResult.OnError("No Internet Connection")
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