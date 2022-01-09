package com.example.myassayment.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myassayment.data.local.Entitys.PreviousBooking
import com.example.myassayment.databinding.LayoutBookingApponitemntItemBinding
import com.example.myassayment.databinding.LayoutPreBookingItemBinding
import com.example.myassayment.models.Appointeiment
import com.example.myassayment.utils.MyDiff
import java.util.*

class PreBookingItemAdapter: RecyclerView.Adapter<PreBookingItemAdapter.Vh>() {
    private var prebookingList= mutableListOf<PreviousBooking>()

    class Vh(var view: LayoutPreBookingItemBinding): RecyclerView.ViewHolder(view.root){

        companion object{
            fun from(parent: ViewGroup):Vh
            {
                val myView= LayoutPreBookingItemBinding.inflate(
                    LayoutInflater.from(parent.context),parent,false)
                return Vh(myView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh.from(parent)
    }

    override fun onBindViewHolder(holder: PreBookingItemAdapter.Vh, position: Int) {
        holder.view.imgPrebooking.load(prebookingList[position].appointement?.doctor?.photo){
        }
        holder.view.nameTitlePrebooking.text=prebookingList[position].appointement?.doctor?.nameEN
        holder.view.spaitaltyPrebooking.text=prebookingList[position].appointement?.doctor?.spicialty
        holder.view.tvDatePrebooking.text=prebookingList[position].appointement?.timeSchedule?.getAllDate()
        holder.view.tvPymentstatusPrebooking.text="unPay"
        holder.view.tvStatusPrebooking.text=prebookingList[position].status
        if (prebookingList[position].status=="cancled")
        {
            holder.view.tvStatusPrebooking.setTextColor(Color.parseColor("#FFFD0000"))
        }
    }

    override fun getItemCount(): Int {
        return prebookingList.size
    }

    fun setData(newList:MutableList<PreviousBooking>)
    {
        val mydiff= MyDiff(prebookingList,newList)
        val result= DiffUtil.calculateDiff(mydiff)
        prebookingList=newList
        result.dispatchUpdatesTo(this)
    }

}
