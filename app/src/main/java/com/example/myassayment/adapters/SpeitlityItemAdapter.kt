package com.example.myassayment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myassayment.databinding.LayoutBestDoctorItemBinding
import com.example.myassayment.databinding.LayoutSpeitalityItemBinding
import com.example.myassayment.models.Doctor
import com.example.myassayment.models.Speitality
import com.example.myassayment.utils.MyDiff

class SpeitlityItemAdapter(
    itemCllickListener: SpeitlityItemAdapter.SpitialItemClick
): RecyclerView.Adapter<SpeitlityItemAdapter.Vh>() {
    private var speitalityList= mutableListOf<Speitality>()
    private var itemListener=itemCllickListener

    class Vh(var view: LayoutSpeitalityItemBinding): RecyclerView.ViewHolder(view.root){

        companion object{
            fun from(parent: ViewGroup):Vh
            {
                val myView= LayoutSpeitalityItemBinding.inflate(
                    LayoutInflater.from(parent.context),parent,false)
                return Vh(myView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh.from(parent)
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.view.imgSpietialItem.load(speitalityList[position].img){
        }
        holder.view.tvNameSpietialItem.text=speitalityList[position].name

        holder.itemView.setOnClickListener {
            itemListener.itembestSpeitalityClick(speitalityList[position].name!!)
        }
    }

    override fun getItemCount(): Int {
        return speitalityList.size
    }

    fun setData(newList:MutableList<Speitality>)
    {
        val mydiff= MyDiff(speitalityList,newList)
        val result= DiffUtil.calculateDiff(mydiff)
        speitalityList=newList
        result.dispatchUpdatesTo(this)
    }
    public interface SpitialItemClick{
        fun itembestSpeitalityClick(speiality:String)

    }
}