package com.example.myassayment.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.myassayment.data.remote.FirebaseSource
import com.example.myassayment.models.Appointeiment
import com.example.myassayment.models.Doctor
import com.example.myassayment.models.TimeSchedule
import com.example.myassayment.utils.MyUtils
import com.example.myassayment.utils.StatusResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val firebase:FirebaseSource,
    application: Application
):AndroidViewModel(application) {

    private var _dateScedulaDoctor: MutableLiveData<StatusResult<MutableList<TimeSchedule>>> =
        MutableLiveData()
    private var _doctorById: MutableStateFlow<StatusResult<Doctor>> =
        MutableStateFlow(StatusResult.OnLoading())
    private var _isUploadedappointement: MutableStateFlow<StatusResult<Boolean>> =
        MutableStateFlow(StatusResult.OnLoading())
    private var _bookAppointement: MutableLiveData<StatusResult<Boolean>> =
        MutableLiveData()

    val dateScedulaDoctor: MutableLiveData<StatusResult<MutableList<TimeSchedule>>> =_dateScedulaDoctor
    val doctorById: StateFlow<StatusResult<Doctor>> =_doctorById
    val isUploadedappointement: StateFlow<StatusResult<Boolean>> =_isUploadedappointement
    val bookAppointement: MutableLiveData<StatusResult<Boolean>> =_bookAppointement

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

    fun getDoctorById(doctorId: String)=firebase.getDoctorById(doctorId)
        .addOnCompleteListener {
            if (it.isSuccessful)
            {
                var doctor=it.result.let { it.toObject(Doctor::class.java)!!}
                _doctorById.value=StatusResult.OnSuccess(doctor)
            }else{
                _doctorById.value=StatusResult.OnError(it.exception?.message.toString())
            }
        }

    fun uploadAppointementToDoctor(appointeiment: Appointeiment,doctorId:String){
        firebase.uploadAppointementToDoctor(appointeiment,doctorId)
            .addOnCompleteListener {
                if (it.isSuccessful)
                {
                    _isUploadedappointement.value=StatusResult.OnSuccess(true)
                }else{
                    _isUploadedappointement.value=StatusResult.OnError(it.exception?.message.toString())
                }
            }
    }

    fun bookDate(doctorsId: String, timeSelected: TimeSchedule) {
        bookAppointement.value=StatusResult.OnLoading()
        if (hasInternetConnection())
        {
            firebase.uploadMyAppointement(
                Appointeiment(
                    timeSchedule = timeSelected,
                    timeBook = Calendar.getInstance().time.toString(),
                    clientId = firebase.user?.uid
                ),
                doctorId = doctorsId
            ).addOnCompleteListener {
                if (it.isSuccessful)
                {
                    bookAppointement.value=StatusResult.OnSuccess(true)
                }else{
                    bookAppointement.value=StatusResult.OnError("${it.exception?.message}")
                }
            }
        }else{
            bookAppointement.value=StatusResult.OnError("No Internet Connection")
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