package com.example.harmonycare.ui.checklist

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.harmonycare.data.Checklist
import com.example.harmonycare.databinding.ChecklistDialogBinding
import com.example.harmonycare.databinding.FragmentChecklistBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.time.LocalDateTime

class ChecklistFragment : Fragment() {

    private var _binding: FragmentChecklistBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentChecklistBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ChecklistAdapter(getDataList()) { checklist ->
            showDetailDialog(checklist)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        binding.fab.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(requireContext())
            val bottomDialogBinding = ChecklistDialogBinding.inflate(layoutInflater)
            bottomSheetDialog.setContentView(bottomDialogBinding.root)
            bottomDialogBinding.timePicker.setIs24HourView(true)
            bottomSheetDialog.show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDataList(): List<Checklist> {
        // 데이터를 가져오는 로직을 구현
        return listOf(
            Checklist("Title 1", "Caption 1", arrayOf("Monday", "TuesDay"), LocalDateTime.now(), false),
            Checklist("Title 2", "Caption 2", arrayOf("Monday", "TuesDay"), LocalDateTime.now(), false),
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDetailDialog(checklist: Checklist) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val dialogBinding = ChecklistDialogBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(dialogBinding.root)

        dialogBinding.editText.setText(checklist.title)
        dialogBinding.timePicker.setIs24HourView(true)
        dialogBinding.timePicker.hour = checklist.checkTime.hour
        dialogBinding.timePicker.minute = checklist.checkTime.minute

        dialogBinding.buttonClose.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        dialogBinding.buttonSave.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }
}