package com.example.myassayment.utils

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
}