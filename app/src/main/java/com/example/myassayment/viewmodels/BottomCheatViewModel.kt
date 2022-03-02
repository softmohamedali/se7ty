package com.example.myassayment.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myassayment.data.remote.FirebaseSource
import com.example.myassayment.models.Massage
import com.example.myassayment.utils.StatusResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BottomCheatViewModel @Inject constructor(
    var firebaseSource: FirebaseSource,
    application: Application
) : AndroidViewModel(application) {
    fun currntuser()=firebaseSource.user()
    private var _isUploadMassage = MutableLiveData<StatusResult<Boolean>>()

    val isUploadMassage: LiveData<StatusResult<Boolean>> = _isUploadMassage

    fun upLoadMassage(msg: Massage) {
        _isUploadMassage.value = StatusResult.OnLoading()
        if (hasInternetConnection()) {
            val ref = firebaseSource.upLoadMassage()
            val id=ref.id
            msg.id=id
            ref.set(msg).addOnCompleteListener {
                if (it.isSuccessful) {
                    _isUploadMassage.value = StatusResult.OnSuccess(true)
                } else {
                    _isUploadMassage.value = StatusResult.OnError(it.exception?.message.toString())
                }
            }
        } else {
            _isUploadMassage.value = StatusResult.OnError("No Interent Connection")
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