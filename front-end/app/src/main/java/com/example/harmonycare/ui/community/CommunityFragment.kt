package com.example.harmonycare.ui.community

import android.R
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.harmonycare.data.Post
import com.example.harmonycare.databinding.ChecklistDialogBinding
import com.example.harmonycare.databinding.CommunityDialogBinding
import com.example.harmonycare.databinding.FragmentCommunityBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.time.LocalDateTime

class CommunityFragment : Fragment() {

    private var _binding: FragmentCommunityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCommunityBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PostAdapter(getDataList()) { checklist ->
            showDetailDialog(checklist)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        binding.fab.setOnClickListener {
            val fullDialog = Dialog(requireContext(), R.style.Theme_Translucent_NoTitleBar_Fullscreen)
            val fullDialogBinding = CommunityDialogBinding.inflate(layoutInflater)
            fullDialog.setContentView(fullDialogBinding.root)

            fullDialogBinding.buttonClose.setOnClickListener {
                fullDialog.dismiss()
            }
            fullDialogBinding.buttonSave.setOnClickListener {
                fullDialog.dismiss()
            }

            fullDialog.show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDataList(): List<Post> {
        // 데이터를 가져오는 로직을 구현
        return listOf(
            Post("Title 1", "Caption 1", 1, LocalDateTime.now(), "Jane", 1),
            Post("Title 2", "Caption 2", 2, LocalDateTime.now(), "Jane", 1),
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDetailDialog(post: Post) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val dialogBinding = ChecklistDialogBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(dialogBinding.root)


        bottomSheetDialog.show()
    }
}