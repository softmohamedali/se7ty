package com.example.myassayment.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myassayment.databinding.LayoutDateItemBinding
import com.example.myassayment.models.Client
import com.example.myassayment.models.TimeSchedule
import com.example.myassayment.utils.MyDiff

class DateAdapter (
    itemCllickListener: DateAdapter.DataSchedulementItemClick
): RecyclerView.Adapter<DateAdapter.Vh>() {
    private var dateScheduleList= mutableListOf<TimeSchedule>()
    private var itemListener=itemCllickListener
    private var viewsHolder = mutableListOf<DateAdapter.Vh>()
    private var viewsHolderPosition = mutableListOf<Int>()
    class Vh(var view: LayoutDateItemBinding): RecyclerView.ViewHolder(view.root){

        companion object{
            fun from(parent: ViewGroup):Vh
            {
                val myView= LayoutDateItemBinding.inflate(
                    LayoutInflater.from(parent.context),parent,false)
                return Vh(myView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh.from(parent)
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        viewsHolder.add(holder)
        viewsHolderPosition.add(position)
        holder.view.fromTimeTv.text="From : ${dateScheduleList[position].fromTime}"
        holder.view.toTimeTv.text="To : ${dateScheduleList[position].toTime}"
        holder.view.dateTv.text="Date : ${dateScheduleList[position].date}"
        holder.itemView.setOnClickListener {view->
            itemListener.itemClick(dateScheduleList[position])
            holder.view.rbDateItem.isChecked=true
            val pos=position
            viewsHolderPosition.forEach {
                if (it!=pos){
                    viewsHolder[it].view.rbDateItem.isChecked=false
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return dateScheduleList.size
    }

    fun setData(newList:MutableList<TimeSchedule>)
    {
        val mydiff= MyDiff(dateScheduleList,newList)
        val result= DiffUtil.calculateDiff(mydiff)
        dateScheduleList=newList
        result.dispatchUpdatesTo(this)
    }
    public interface DataSchedulementItemClick{
        fun itemClick(time:TimeSchedule)
    }
}
