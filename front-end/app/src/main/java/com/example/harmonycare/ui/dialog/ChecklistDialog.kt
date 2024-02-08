package com.example.harmonycare.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import com.example.harmonycare.databinding.ChecklistDialogBinding

class ChecklistDialog(context: Context, dialogStyle: Int) : Dialog(context) {
    private lateinit var binding: ChecklistDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ChecklistDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutParams = window?.attributes
        layoutParams?.apply {
            gravity = Gravity.CENTER
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
        }

        binding.timePicker.setIs24HourView(true)

        binding.buttonClose.setOnClickListener {
            dismiss()
        }
        binding.buttonSave.setOnClickListener {
            //저장하기
            dismiss()
        }
    }
}
