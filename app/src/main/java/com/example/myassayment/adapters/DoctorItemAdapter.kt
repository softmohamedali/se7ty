package com.example.myassayment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myassayment.databinding.LayoutBestDoctorItemBinding
import com.example.myassayment.databinding.LayoutDoctorItemBinding
import com.example.myassayment.models.Doctor
import com.example.myassayment.utils.MyDiff

class DoctorItemAdapter (
    itemCllickListener: DoctorItemAdapter.BestDoctorItemClick
): RecyclerView.Adapter<DoctorItemAdapter.Vh>() {
    private var doctorList= mutableListOf<Doctor>()
    private var itemListener=itemCllickListener

    class Vh(var view: LayoutDoctorItemBinding): RecyclerView.ViewHolder(view.root){

        companion object{
            fun from(parent: ViewGroup):Vh
            {
                val myView= LayoutDoctorItemBinding.inflate(
                    LayoutInflater.from(parent.context),parent,false)
                return Vh(myView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh.from(parent)
    }

    override fun onBindViewHolder(holder: DoctorItemAdapter.Vh, position: Int) {
        holder.view.imgDoctoritem.load(doctorList[position].photo){
        }
        holder.view.nameTitleDoctoritem.text=doctorList[position].nameEN
        holder.view.spaitaltyDoctoritem.text=doctorList[position].spicialty
        holder.itemView.setOnClickListener {
            itemListener.itembestDoctorClick(doctorList[position])
        }
        holder.view.bookBtnDoctoritem.setOnClickListener {
            itemListener.bookBtnClick(doctorList[position])
        }
    }

    override fun getItemCount(): Int {
        return doctorList.size
    }

    fun setData(newList:MutableList<Doctor>)
    {
        val mydiff= MyDiff(doctorList,newList)
        val result= DiffUtil.calculateDiff(mydiff)
        doctorList=newList
        result.dispatchUpdatesTo(this)
    }
    public interface BestDoctorItemClick{
        fun itembestDoctorClick(doctor: Doctor)
        fun bookBtnClick(doctor: Doctor)
    }
}

