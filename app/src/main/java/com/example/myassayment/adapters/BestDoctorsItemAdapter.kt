package com.example.myassayment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myassayment.databinding.LayoutBestDoctorItemBinding
import com.example.myassayment.databinding.LayoutLatestServicesItemBinding
import com.example.myassayment.models.Doctor
import com.example.myassayment.models.Sevices
import com.example.myassayment.utils.MyDiff

class BestDoctorsItemAdapter (
    itemCllickListener: BestDoctorsItemAdapter.BestDoctorItemClick
): RecyclerView.Adapter<BestDoctorsItemAdapter.Vh>() {
    private var bestDoctorList= mutableListOf<Doctor>()
    private var itemListener=itemCllickListener

    class Vh(var view: LayoutBestDoctorItemBinding): RecyclerView.ViewHolder(view.root){

        companion object{
            fun from(parent: ViewGroup):Vh
            {
                val myView= LayoutBestDoctorItemBinding.inflate(
                    LayoutInflater.from(parent.context),parent,false)
                return Vh(myView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh.from(parent)
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.view.imgImg.load(bestDoctorList[position].photo){
        }
        holder.view.nameTv.text=bestDoctorList[position].nameEN
        holder.view.spitaltiestTv.text=bestDoctorList[position].spicialty
        holder.itemView.setOnClickListener {
            itemListener.itembestDoctorClick(bestDoctorList[position])
        }
        holder.view.bookNowBtn.setOnClickListener {
            itemListener.bookBtnClick(bestDoctorList[position])
        }
    }

    override fun getItemCount(): Int {
        return bestDoctorList.size
    }

    fun setData(newList:MutableList<Doctor>)
    {
        val mydiff= MyDiff(bestDoctorList,newList)
        val result= DiffUtil.calculateDiff(mydiff)
        bestDoctorList=newList
        result.dispatchUpdatesTo(this)
    }
    public interface BestDoctorItemClick{
        fun itembestDoctorClick(doctor: Doctor)
        fun bookBtnClick(doctor: Doctor)
    }
}

