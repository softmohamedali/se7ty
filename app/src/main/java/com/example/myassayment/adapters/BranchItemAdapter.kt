package com.example.myassayment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myassayment.R
import com.example.myassayment.databinding.LayoutBranchItemBinding
import com.example.myassayment.databinding.LayoutDateItemBinding
import com.example.myassayment.models.Branche
import com.example.myassayment.models.TimeSchedule
import com.example.myassayment.utils.MyDiff

class BranchItemAdapter(
    itemCllickListener: BranchItemAdapter.BranchItemClick,
    var context: Context
): RecyclerView.Adapter<BranchItemAdapter.Vh>() {
    private var branchList= mutableListOf<Branche>()
    private var itemListener=itemCllickListener
    private var viewsHolder = mutableListOf<BranchItemAdapter.Vh>()
    private var viewsHolderPosition = mutableListOf<Int>()
    class Vh(var view: LayoutBranchItemBinding): RecyclerView.ViewHolder(view.root){

        companion object{
            fun from(parent: ViewGroup):Vh
            {
                val myView= LayoutBranchItemBinding.inflate(
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
        holder.view.pricevisitPrebooking.text="${branchList[position].pricebook} E.G"
        holder.view.nameBranch.text=branchList[position].name
        holder.view.imgBranch.load(branchList[position].img)
        holder.itemView.setOnClickListener {view->
            itemListener.itemClick(branchList[position])
            holdercolor(holder,true)
            val pos=position
            viewsHolderPosition.forEach {
                if (it!=pos){
                    holdercolor(viewsHolder[it],false)
                }
            }
        }
    }

    private fun holdercolor(holder: Vh,cheaked:Boolean) {
        val mainColor=ContextCompat.getColor(context, R.color.background_tool)
        val secColor=ContextCompat.getColor(context, R.color.white)
        if (cheaked){
            holder.view.branchContainer.setBackgroundColor(mainColor)
            holder.view.pricevisitPrebooking.setTextColor(secColor)
            holder.view.nameBranch.setTextColor(secColor)
        }else{
            holder.view.branchContainer.setBackgroundColor(secColor)
            holder.view.pricevisitPrebooking.setTextColor(mainColor)
            holder.view.nameBranch.setTextColor(mainColor)
        }
    }

    override fun getItemCount(): Int {
        return branchList.size
    }

    fun setData(newList:MutableList<Branche>)
    {
        val mydiff= MyDiff(branchList,newList)
        val result= DiffUtil.calculateDiff(mydiff)
        branchList=newList
        result.dispatchUpdatesTo(this)
    }
    public interface BranchItemClick{
        fun itemClick(branche:Branche)
    }
}
