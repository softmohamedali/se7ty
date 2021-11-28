package com.example.myassayment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myassayment.databinding.LayoutLatestServicesItemBinding
import com.example.myassayment.models.Sevices
import com.example.myassayment.utils.MyDiff

class ServicesItemadapter (
    itemCllickListener: ServicesItemadapter.TodyAppointementItemClick
): RecyclerView.Adapter<ServicesItemadapter.Vh>() {
    private var servicestList= mutableListOf<Sevices>()
    private var itemListener=itemCllickListener

    class Vh(var view: LayoutLatestServicesItemBinding): RecyclerView.ViewHolder(view.root){

        companion object{
            fun from(parent: ViewGroup):Vh
            {
                val myView= LayoutLatestServicesItemBinding.inflate(
                    LayoutInflater.from(parent.context),parent,false)
                return Vh(myView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh.from(parent)
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.view.imgImg.setImageResource(servicestList[position].img!!)
        holder.view.nameTv.text=servicestList[position].name
        holder.itemView.setOnClickListener {
            itemListener.itemClick(servicestList[position],position)
        }
    }

    override fun getItemCount(): Int {
        return servicestList.size
    }

    fun setData(newList:MutableList<Sevices>)
    {
        val mydiff= MyDiff(servicestList,newList)
        val result= DiffUtil.calculateDiff(mydiff)
        servicestList=newList
        result.dispatchUpdatesTo(this)
    }
    public interface TodyAppointementItemClick{
        fun itemClick(services:Sevices,pos:Int)
    }
}

