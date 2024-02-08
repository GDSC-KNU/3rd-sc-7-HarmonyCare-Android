package com.example.harmonycare.ui.checklist

import com.example.harmonycare.databinding.ChecklistItemBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.harmonycare.R
import com.example.harmonycare.data.Checklist

class ChecklistAdapter(private val dataList: List<Checklist>, private val onItemClick: (Checklist) -> Unit) : RecyclerView.Adapter<ChecklistAdapter.ChecklistViewHolder>() {
    inner class ChecklistViewHolder(private val binding: ChecklistItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val checklist = dataList[position]
                    onItemClick(checklist)
                }
            }
            binding.buttonCheckbox.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val checklist = dataList[position]
                    checklist.isDone = !checklist.isDone
                    updateToggleButton(binding, checklist.isDone)
                }
            }
        }
        fun bind(checklist: Checklist) {
            binding.textTitle.text = checklist.title
            binding.textCaption.text = checklist.caption
        }
        private fun updateToggleButton(binding: ChecklistItemBinding, isSelected: Boolean) {
            binding.buttonCheckbox.setImageResource(if (isSelected) R.drawable.icon_checkbox_orange else R.drawable.icon_checkbox_gray)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChecklistViewHolder {
        val binding = ChecklistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChecklistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChecklistViewHolder, position: Int) {
        val checklist = dataList[position]
        holder.bind(checklist)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}