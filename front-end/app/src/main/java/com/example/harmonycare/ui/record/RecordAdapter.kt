package com.example.harmonycare.ui.record

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.harmonycare.R
import com.example.harmonycare.databinding.RecordItemBinding
import com.example.harmonycare.data.Record

class RecordAdapter(val context: Context, private val dataList: List<Record>, private val onItemClick: (Record) -> Unit) : RecyclerView.Adapter<RecordAdapter.RecordViewHolder>() {
    inner class RecordViewHolder(private val binding: RecordItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val record = dataList[position]
                    onItemClick(record)
                }
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(record: Record) {
            when (record.type) {
                "Sleep"-> {
                    binding.textTitle.text = "Sleep"
                    binding.textTitle.setTextColor(ContextCompat.getColor(context, R.color.sleep_blue))
                    binding.textCircle.setTextColor(ContextCompat.getColor(context, R.color.sleep_blue))
                }
                "Meal" -> {
                    binding.textTitle.text = "Meal"
                    binding.textTitle.setTextColor(ContextCompat.getColor(context, R.color.meal_green))
                    binding.textCircle.setTextColor(ContextCompat.getColor(context, R.color.meal_green))
                }
                "Play" -> {
                    binding.textTitle.text = "Play"
                    binding.textTitle.setTextColor(ContextCompat.getColor(context, R.color.play_purple))
                    binding.textCircle.setTextColor(ContextCompat.getColor(context, R.color.play_purple))
                }
                "Diaper" -> {
                    binding.textTitle.text = "Diaper"
                    binding.textTitle.setTextColor(ContextCompat.getColor(context, R.color.diaper_yellow))
                    binding.textCircle.setTextColor(ContextCompat.getColor(context, R.color.diaper_yellow))
                }
                "Bath" -> {
                    binding.textTitle.text = "Bath"
                    binding.textTitle.setTextColor(ContextCompat.getColor(context, R.color.bath_orange))
                    binding.textCircle.setTextColor(ContextCompat.getColor(context, R.color.bath_orange))
                }
            }
            binding.textCaption.text = record.caption
            val hour = record.recordTime.hour
            if (hour < 12) binding.textAmpm.text = "AM" else binding.textAmpm.text = "PM"
            if (record.recordTime.hour % 12 == 0) 12 else record.recordTime.hour % 12
            binding.textTime.text = "${hour.toString().padStart(2, '0')}:${record.recordTime.minute.toString().padStart(2, '0')}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val binding = RecordItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecordViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        val record = dataList[position]
        holder.bind(record)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
