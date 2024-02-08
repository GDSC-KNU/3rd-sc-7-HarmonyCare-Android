package com.example.harmonycare.ui.record

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.harmonycare.R
import com.example.harmonycare.databinding.FragmentRecordBinding
import com.example.harmonycare.data.Record
import com.example.harmonycare.databinding.RecordDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.time.LocalDateTime

class RecordFragment : Fragment() {

    private var _binding: FragmentRecordBinding? = null
    private val binding get() = _binding!!
    private var isFabOpen = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRecordBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RecordAdapter(requireContext(), getDataList()) { record ->
            showDetailDialog(record)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        binding.dateTextView.text = LocalDateTime.now().toLocalDate().toString()
        binding.fabAdd.setOnClickListener {
            toggleFab()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDataList(): List<Record> {
        // 데이터를 가져오는 로직을 구현
        return listOf(
            Record("Sleep", "Caption 1", LocalDateTime.now()),
            Record("Diaper", "Caption 2", LocalDateTime.now())
        )
    }

    private fun toggleFab() {
        // 플로팅 액션 버튼 닫기 - 열려있는 플로팅 버튼 집어넣는 애니메이션 세팅
        if (isFabOpen) {
            ObjectAnimator.ofFloat(binding.fabSleep, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabMeal, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabPlay, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabDiaper, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabBath, "translationY", 0f).apply { start() }
            binding.fabAdd.setImageResource(R.drawable.icon_add)

            // 플로팅 액션 버튼 열기 - 닫혀있는 플로팅 버튼 꺼내는 애니메이션 세팅
        } else {
            ObjectAnimator.ofFloat(binding.fabSleep, "translationY", -200f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabMeal, "translationY", -400f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabPlay, "translationY", -600f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabDiaper, "translationY", -800f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabBath, "translationY", -1000f).apply { start() }
            binding.fabAdd.setImageResource(R.drawable.icon_close)
        }

        isFabOpen = !isFabOpen

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDetailDialog(record: Record) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val dialogBinding = RecordDialogBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(dialogBinding.root)

        when (record.type) {
            "Sleep" -> {
                dialogBinding.titleTextView.text = "Sleep"
                dialogBinding.topBar.setBackgroundColor(resources.getColor(R.color.sleep_blue))
            }
            "Meal" -> {
                dialogBinding.titleTextView.text = "Meal"
                dialogBinding.topBar.setBackgroundColor(resources.getColor(R.color.meal_green))
            }
            "Play" -> {
                dialogBinding.titleTextView.text = "Play"
                dialogBinding.topBar.setBackgroundColor(resources.getColor(R.color.play_purple))
            }
            "Diaper" -> {
                dialogBinding.titleTextView.text = "Diaper"
                dialogBinding.topBar.setBackgroundColor(resources.getColor(R.color.diaper_yellow))
            }
            "Bath" -> {
                dialogBinding.titleTextView.text = "Bath"
                dialogBinding.topBar.setBackgroundColor(resources.getColor(R.color.bath_orange))
            }
        }
        dialogBinding.editText.setText(record.caption)
        dialogBinding.timePicker.hour = record.recordTime.hour
        dialogBinding.timePicker.minute = record.recordTime.minute

        dialogBinding.buttonClose.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        dialogBinding.buttonSave.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }
}
