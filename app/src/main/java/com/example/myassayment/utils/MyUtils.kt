package com.example.myassayment.utils

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import com.example.myassayment.R
import com.example.myassayment.databinding.LayoutToastSuccessBinding
import com.example.myassayment.models.Client
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import dagger.hilt.android.internal.Contexts.getApplication
import javax.inject.Inject

object MyUtils {

    inline fun <reified T> handledata(value: QuerySnapshot?): StatusResult<MutableList<T>> {
        val data = mutableListOf<T>()
        if (value != null) {
            value.documents.forEach {
                data.add(it.toObject(T::class.java)!!)
            }
            if (data.isEmpty()) {
                return StatusResult.OnError(msg = "No data Found")
            } else {
                return StatusResult.OnSuccess(data = data)
            }
        } else {
            return StatusResult.OnError(msg = "No data Found")
        }
    }

    inline fun <reified T> handleSingledata(it: Task<DocumentSnapshot>): StatusResult<T>? {
        if (it.result != null) {
            Log.d("mylog", "${it.result.toObject(T::class.java)!!}")
            return StatusResult.OnSuccess(data = it.result.toObject(T::class.java)!!)
        } else {
            return StatusResult.OnError(msg = "No data Found")
        }
    }

    fun toastSuccessBooking(context: Context, msg: String) {
        var toast = Toast(context)
        toast.duration = Toast.LENGTH_LONG
        val view = LayoutToastSuccessBinding.inflate(LayoutInflater.from(context))
        view.textView9.text = msg
        toast.view = view.root
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

}