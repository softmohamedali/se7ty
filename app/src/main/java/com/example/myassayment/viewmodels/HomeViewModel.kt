package com.example.myassayment.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.myassayment.data.remote.FirebaseSource
import com.example.myassayment.models.Doctor
import com.example.myassayment.utils.StatusResult
import com.google.firebase.firestore.QuerySnapshot
import dagger.hilt.android.internal.Contexts
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val firebase: FirebaseSource,
    application:Application
): AndroidViewModel (application){

    private var _doctors:MutableLiveData<StatusResult<MutableList<Doctor>>> = MutableLiveData()

    val doctor:MutableLiveData<StatusResult<MutableList<Doctor>>> =_doctors
    val user=firebase.user()

    fun getDoctors() {
        if (hasInternetConnection()) {
            firebase.getDoctors().addSnapshotListener { value, error ->
                if (error == null) {
                    _doctors.value=handledata<Doctor>(value)
                } else {
                    _doctors.value=StatusResult.OnError("No internet Connection")
                }
            }
        } else {
            _doctors.value=StatusResult.OnError("No internet Connection")
        }

    }

    private inline fun <reified T>handledata(value: QuerySnapshot?): StatusResult<MutableList<T>>? {
        val data= mutableListOf<T>()
        if (value!=null)
        {
            value.documents.forEach {
                data.add(it.toObject(T::class.java)!!)
            }
            if (data.isEmpty())
            {
                return StatusResult.OnError(msg = "No data Found")
            }
            else{
                return StatusResult.OnSuccess(data = data)
            }
        }else{
            return StatusResult.OnError(msg = "No data Found")
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