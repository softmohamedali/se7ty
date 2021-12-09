package com.example.myassayment.utils

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import com.example.myassayment.R
import com.example.myassayment.databinding.LayoutToastSuccessBinding
import com.google.firebase.firestore.QuerySnapshot

object MyUtils {

     inline fun <reified T>handledata(value: QuerySnapshot?): StatusResult<MutableList<T>>? {
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

    fun toastSuccessBooking(context: Context){
        var toast=Toast(context)
        toast.duration=Toast.LENGTH_LONG
        toast.view=LayoutInflater.from(context).inflate(R.layout.layout_toast_success,null,false)
        toast.setGravity(Gravity.CENTER,0,0)
        toast.show()
    }
}