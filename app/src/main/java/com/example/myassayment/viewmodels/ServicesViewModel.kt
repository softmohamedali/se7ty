package com.example.myassayment.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myassayment.data.remote.FirebaseSource
import com.example.myassayment.models.BookTest
import com.example.myassayment.models.Branche
import com.example.myassayment.models.LapTests
import com.example.myassayment.utils.MyUtils
import com.example.myassayment.utils.StatusResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ServicesViewModel @Inject constructor(
    private var firebaseSource: FirebaseSource,
    application: Application
):AndroidViewModel(application) {

    private var _lapTests:MutableStateFlow<StatusResult<MutableList<LapTests>>?> =
        MutableStateFlow(null)
    val lapTests:StateFlow<StatusResult<MutableList<LapTests>>?> = _lapTests

    private var _coviedTests= MutableStateFlow<StatusResult<MutableList<LapTests>>?>(null)
    val coviedTests: StateFlow<StatusResult<MutableList<LapTests>>?> =_coviedTests


    private var _bookLapTest: MutableLiveData<StatusResult<Boolean>> =
        MutableLiveData()
    val bookLapTest: LiveData<StatusResult<Boolean>> =_bookLapTest

    private var _allBranches: MutableStateFlow<StatusResult<MutableList<Branche>>?> =
        MutableStateFlow(null)
    val allBranches: StateFlow<StatusResult<MutableList<Branche>>?> =_allBranches

    fun getAllLapTests(){
        _lapTests.value=StatusResult.OnLoading()
        if (hasInternetConnection())
        {
            firebaseSource.getLapTests().addSnapshotListener { value, error ->
                if (error==null)
                {
                    _lapTests.value=MyUtils.handledata(value)
                }else{
                    _lapTests.value=StatusResult.OnError("${error.message}")
                }
            }
        }else{
            _lapTests.value=StatusResult.OnError("No Internet Connection")
        }
    }

    fun getAllCoviedTests(){
        _coviedTests.value=StatusResult.OnLoading()
        if (hasInternetConnection())
        {
            firebaseSource.getCoviedTests().addSnapshotListener { value, error ->
                if (error==null)
                {
                    _coviedTests.value=MyUtils.handledata(value)
                }else{
                    _coviedTests.value=StatusResult.OnError("${error.message}")
                }
            }
        }else{
            _coviedTests.value=StatusResult.OnError("No Internet Connection")
        }
    }

    fun getSearchLapTests(name:String){
        _lapTests.value=StatusResult.OnLoading()
        if (hasInternetConnection())
        {
            firebaseSource.getSearchLapTests(name).addSnapshotListener { value, error ->
                if (error==null)
                {
                    _lapTests.value=MyUtils.handledata(value)
                }else{
                    _lapTests.value=StatusResult.OnError("${error.message}")
                }
            }
        }else{
            _lapTests.value=StatusResult.OnError("No Internet Connection")
        }
    }

    fun bookLapTest(bookTest: BookTest){
        _bookLapTest.value=StatusResult.OnLoading()
        if (hasInternetConnection()){
            val ref=firebaseSource.bookLapTests(bookTest).document()
            val mybook=bookTest
            bookTest.id=ref.id
            bookTest.userId=firebaseSource.user()!!.uid
            ref.set(bookTest).addOnCompleteListener {
                if (it.isSuccessful)
                {
                    _bookLapTest.value=StatusResult.OnSuccess()
                }else{
                    _bookLapTest.value=StatusResult.OnError(it.exception?.message.toString())
                }
            }
        }else{
            _bookLapTest.value=StatusResult.OnError("No Internet Connection")
        }
    }

    fun getAllBranches(area:String,city:String)
    {
        _allBranches.value=StatusResult.OnLoading()
        if (hasInternetConnection()){
            firebaseSource.getAllBranches(area, city).addSnapshotListener { value, error ->
                if (error==null)
                {
                    _allBranches.value=MyUtils.handledata(value)
                }else{
                    _allBranches.value=StatusResult.OnError(error.message.toString())
                }
            }
        }else{
            _allBranches.value=StatusResult.OnError("No Internet Connection")
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