package com.example.myassayment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myassayment.databinding.LayoutMedicalTestItemBinding
import com.example.myassayment.databinding.LayoutMedicationItemBinding
import com.example.myassayment.models.Medecation
import com.example.myassayment.models.MedicalTest
import com.example.myassayment.utils.MyDiff

class MedicationItemAdapter(
    itemCllickListener: MedicationItemAdapter.MedicationItemClick
) : RecyclerView.Adapter<MedicationItemAdapter.Vh>() {
    private var userMedicationList = mutableListOf<Medecation>()
    private var itemListener = itemCllickListener

    class Vh(var view: LayoutMedicationItemBinding) : RecyclerView.ViewHolder(view.root) {

        companion object {
            fun from(parent: ViewGroup): Vh {
                val myView = LayoutMedicationItemBinding.inflate(
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
        holder.view.btnEdit.setOnClickListener {
            itemListener.btnEditClick(userMedicationList[position])
        }
        holder.view.tvMedicationName.text=userMedicationList[position].medicationName
        holder.view.tvNote.text=userMedicationList[position].notes
    }

    override fun getItemCount(): Int {
        return userMedicationList.size
    }

    fun setData(newList: MutableList<Medecation>) {
        val mydiff = MyDiff(userMedicationList, newList)
        val result = DiffUtil.calculateDiff(mydiff)
        userMedicationList = newList
        result.dispatchUpdatesTo(this)
    }

    public interface MedicationItemClick {
        fun btnEditClick(medication: Medecation)
    }
}