package com.example.myassayment.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.myassayment.data.local.DataBaseSource
import com.example.myassayment.data.local.Entitys.PreviousBooking
import com.example.myassayment.data.remote.FirebaseSource
import com.example.myassayment.models.*
import com.example.myassayment.utils.MyUtils
import com.example.myassayment.utils.StatusResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val firebase:FirebaseSource,
    private val dataBaseSource: DataBaseSource,
    application: Application
):AndroidViewModel(application) {

    private var _dateScedulaDoctor: MutableLiveData<StatusResult<MutableList<TimeSchedule>>> =
        MutableLiveData()
    private var _doctorById: MutableStateFlow<StatusResult<Doctor>> =
        MutableStateFlow(StatusResult.OnLoading())
    private var _bookAppointement: MutableLiveData<StatusResult<Boolean>> =
        MutableLiveData()
    private var _delteAppointement: MutableLiveData<StatusResult<Boolean>> =
        MutableLiveData()
    private var _myAppointement:MutableStateFlow<StatusResult<MutableList<Appointeiment>>>  =
        MutableStateFlow(StatusResult.OnLoading())
    private var _IsDataSchedulaAdded:MutableLiveData<StatusResult<TimeSchedule>> =
        MutableLiveData()
    private var _IsTimeSchedulaDeleted:MutableLiveData<StatusResult<TimeSchedule>> =
        MutableLiveData()



    val isDataSchedulaAdded: LiveData<StatusResult<TimeSchedule>> = _IsDataSchedulaAdded
    val isTimeSchedulaDeleted: LiveData<StatusResult<TimeSchedule>> = _IsTimeSchedulaDeleted
    val dateScedulaDoctor: LiveData<StatusResult<MutableList<TimeSchedule>>> =_dateScedulaDoctor
    val doctorById: StateFlow<StatusResult<Doctor>> =_doctorById
    val bookAppointement: LiveData<StatusResult<Boolean>> =_bookAppointement
    val delteAppointement: LiveData<StatusResult<Boolean>> =_delteAppointement
    val myAppointement: StateFlow<StatusResult<MutableList<Appointeiment>>> =_myAppointement
    val getAllPreBooking=dataBaseSource.getAllPreBooking().asLiveData()



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


    fun bookDate(doctor: Doctor, timeSelected: TimeSchedule) {
        _bookAppointement.value=StatusResult.OnLoading()
        if (hasInternetConnection())
        {
            firebase.uploadMyAppointement(
                Appointeiment(
                    timeSchedule = timeSelected,
                    timeBook = Calendar.getInstance().time.toString(),
                    clientId = firebase.user()?.uid,
                    doctor = doctor,
                    client = Client(
                        name = firebase.auth.currentUser?.displayName,
                    ),
                    doctorId = doctor.doctorsId
                )
            ).addOnCompleteListener {
                if (it.isSuccessful)
                {
                    _bookAppointement.value=StatusResult.OnSuccess(true)
                    deltelTimeSchedula(timeSelected,doctorId = doctor.doctorsId!!)
                }else{
                    _bookAppointement.value=StatusResult.OnError("${it.exception?.message}")
                }
            }
        }else{
            _bookAppointement.value=StatusResult.OnError("No Internet Connection")
        }

    }

    fun getUserAppointement(){
        _myAppointement.value=StatusResult.OnLoading()
        if (hasInternetConnection()){
            firebase.getUserApponitement().addSnapshotListener { value, error ->
                if (error==null)
                {
                    _myAppointement.value=MyUtils.handledata(value)
                }else{
                    _myAppointement.value=StatusResult.OnError(error.message.toString())
                }
            }
        }else{
            _myAppointement.value=StatusResult.OnError("No Internet Connection")
        }
    }

    fun deltelAppointement(appointeiment: Appointeiment){
        _delteAppointement.value=StatusResult.OnLoading()
        if (hasInternetConnection())
        {
            firebase.delteAppointement(appointeiment.appointeimentId)
                .addOnCompleteListener {
                if (it.isSuccessful)
                {
                    addTimeSchedula(appointeiment.timeSchedule!!,appointeiment.doctor?.doctorsId!!)
                    insertPreBooking(PreviousBooking(appointement = appointeiment,status = "cancled"))
                    _delteAppointement.value=StatusResult.OnSuccess(true)
                }else{
                    _delteAppointement.value=StatusResult.OnError("${it.exception?.message}")
                }
            }
        }else{
            _delteAppointement.value=StatusResult.OnError("No Internet Connection")
        }

    }

    fun deltelTimeSchedula(time: TimeSchedule,doctorId: String){
        _IsTimeSchedulaDeleted.value=StatusResult.OnLoading()
        if (hasInternetConnection())
        {
            firebase.deleteTimeSchedula(timeSchId = time.id!!,doctorId)
                .addOnCompleteListener {
                    if (it.isSuccessful)
                    {
                        _IsTimeSchedulaDeleted.value=StatusResult.OnSuccess(time)
                    }else{
                        _IsTimeSchedulaDeleted.value=StatusResult.OnError("${it.exception?.message}")
                    }
                }
        }else{
            _IsTimeSchedulaDeleted.value=StatusResult.OnError("No Internet Connection")
        }

    }

    fun addTimeSchedula(time:TimeSchedule,doctorId: String){
        _IsDataSchedulaAdded.value=StatusResult.OnLoading()
        if(hasInternetConnection())
        {
            firebase.addTimeSchedula(time,doctorId).addOnCompleteListener {
                if (it.isSuccessful)
                {
                    _IsDataSchedulaAdded.value=StatusResult.OnSuccess()
                }else{
                    _IsDataSchedulaAdded.value=StatusResult.OnError(it.exception?.message.toString())
                }
            }
        }else{
            _IsDataSchedulaAdded.value=StatusResult.OnError("No Internet Connection")
        }
    }

    fun insertPreBooking(previousBooking: PreviousBooking){
        viewModelScope.launch {
            dataBaseSource.insertPreviousBooking(previousBooking)
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