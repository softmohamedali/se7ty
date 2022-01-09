package com.example.myassayment.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myassayment.databinding.LayoutTestsItemBinding
import com.example.myassayment.databinding.LayoutUpcommingBookingItemBinding
import com.example.myassayment.models.Appointeiment
import com.example.myassayment.models.LapTests
import com.example.myassayment.utils.MyDiff

class LapTestsItemAdapter(
    itemCllickListener: LapTestsItemAdapter.LapTestsItemClick
) : RecyclerView.Adapter<LapTestsItemAdapter.Vh>() {
    private var lapTestsList = mutableListOf<LapTests>()
    private var itemListener = itemCllickListener
    private var isCheaked = mutableListOf<Boolean>()
    private var allLapTests = mutableListOf<LapTests>()
    private var allView = mutableListOf<Vh>()
    private var lapTestsCheaked = mutableListOf<LapTests?>()

    class Vh(var view: LayoutTestsItemBinding) : RecyclerView.ViewHolder(view.root) {

        companion object {
            fun from(parent: ViewGroup): Vh {
                val myView = LayoutTestsItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                return Vh(myView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh.from(parent)
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        isCheaked.add(false)
        allLapTests.add(lapTestsList[position])
        allView.add(holder)
        lapTestsCheaked.add(null)
        holder.view.tvLabtestssName.text = lapTestsList[position].name
        holder.itemView.setOnClickListener {
            isCheaked[position] = isCheaked[position] == false
            for (i in 0..isCheaked.size-1) {
                if (isCheaked[i])
                {
                    lapTestsCheaked[i]=allLapTests[i]
                    allView[i].view.imgCorrectLaptestitem.isVisible=true
                }else{
                    lapTestsCheaked[i]=null
                    allView[i].view.imgCorrectLaptestitem.isVisible=false
                }
            }
            getLapTestCheaked()
        }


    }

    override fun getItemCount(): Int {
        return lapTestsList.size
    }

    fun setData(newList: MutableList<LapTests>) {
        val mydiff = MyDiff(lapTestsList, newList)
        val result = DiffUtil.calculateDiff(mydiff)
        lapTestsList = newList
        result.dispatchUpdatesTo(this)
    }
    fun getLapTestCheaked():MutableList<LapTests>{
        val mylaptest= mutableListOf<LapTests>()
        lapTestsCheaked.forEach {
            if (it != null) {
                mylaptest.add(it)
            }
        }
        itemListener.itemupLapTestsClick(mylaptest)
        return mylaptest
    }


    public interface LapTestsItemClick {
        fun itemupLapTestsClick(laptests: MutableList<LapTests>)

    }
}