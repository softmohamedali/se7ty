package com.example.myassayment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myassayment.databinding.LayoutBookingApponitemntItemBinding
import com.example.myassayment.databinding.LayoutDoctorItemBinding
import com.example.myassayment.models.Appointeiment
import com.example.myassayment.models.Doctor
import com.example.myassayment.utils.MyDiff

class AppointementItemAdapter(
    itemCllickListener: AppointementItemAdapter.AppointemtnBookingItemClick
): RecyclerView.Adapter<AppointementItemAdapter.Vh>() {
    private var bookingList= mutableListOf<Appointeiment>()
    private var itemListener=itemCllickListener

    class Vh(var view: LayoutBookingApponitemntItemBinding): RecyclerView.ViewHolder(view.root){

        companion object{
            fun from(parent: ViewGroup):Vh
            {
                val myView= LayoutBookingApponitemntItemBinding.inflate(
                    LayoutInflater.from(parent.context),parent,false)
                return Vh(myView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh.from(parent)
    }

    override fun onBindViewHolder(holder: AppointementItemAdapter.Vh, position: Int) {
        holder.view.imgApponitementitem.load(bookingList[position].doctor?.photo){
        }
        holder.view.nameTitleApponitementitem.text=bookingList[position].doctor?.nameEN
        holder.view.spaitaltyApponitementitem.text=bookingList[position].doctor?.spicialty
        holder.view.tvDateApponitementitem.text=bookingList[position].timeSchedule?.getAllDate()
        holder.view.tvPymentstatusAppointement.text="unPay"
        holder.itemView.setOnClickListener {
            itemListener.itembestDoctorClick(bookingList[position])
        }
    }

    override fun getItemCount(): Int {
        return bookingList.size
    }

    fun setData(newList:MutableList<Appointeiment>)
    {
        val mydiff= MyDiff(bookingList,newList)
        val result= DiffUtil.calculateDiff(mydiff)
        bookingList=newList
        result.dispatchUpdatesTo(this)
    }
    public interface AppointemtnBookingItemClick{
        fun itembestDoctorClick(appointeiment: Appointeiment)
    }
}

