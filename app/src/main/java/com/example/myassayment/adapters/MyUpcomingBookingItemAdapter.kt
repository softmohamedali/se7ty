package com.example.myassayment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myassayment.databinding.LayoutBestDoctorItemBinding
import com.example.myassayment.databinding.LayoutUpcommingBookingItemBinding
import com.example.myassayment.models.Appointeiment
import com.example.myassayment.models.Doctor
import com.example.myassayment.utils.MyDiff

class MyUpcomingBookingItemAdapter(
    itemCllickListener: MyUpcomingBookingItemAdapter.UpComBookingItemClick
): RecyclerView.Adapter<MyUpcomingBookingItemAdapter.Vh>() {
    private var upCommingBookingList= mutableListOf<Appointeiment>()
    private var itemListener=itemCllickListener

    class Vh(var view: LayoutUpcommingBookingItemBinding): RecyclerView.ViewHolder(view.root){

        companion object{
            fun from(parent: ViewGroup):Vh
            {
                val myView= LayoutUpcommingBookingItemBinding.inflate(
                    LayoutInflater.from(parent.context),parent,false)
                return Vh(myView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh.from(parent)
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.view.imgUpcombooking.load(upCommingBookingList[position].doctor?.photo){
        }
        holder.view.nameTvUpcombooking.text=upCommingBookingList[position].doctor?.nameEN
        holder.view.spitaltiestTvUpcombooking.text=upCommingBookingList[position].doctor?.spicialty
        holder.view.dateTimeTvUpcombooking.text="${upCommingBookingList[position].timeSchedule?.getAllDate()}"
        holder.itemView.setOnClickListener {
            itemListener.itemupCommingBookingClick(upCommingBookingList[position])
        }

    }

    override fun getItemCount(): Int {
        return upCommingBookingList.size
    }

    fun setData(newList:MutableList<Appointeiment>)
    {
        val mydiff= MyDiff(upCommingBookingList,newList)
        val result= DiffUtil.calculateDiff(mydiff)
        upCommingBookingList=newList
        result.dispatchUpdatesTo(this)
    }
    public interface UpComBookingItemClick{
        fun itemupCommingBookingClick(upcomBooking: Appointeiment)

    }
}