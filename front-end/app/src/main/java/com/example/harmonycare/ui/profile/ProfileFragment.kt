package com.example.harmonycare.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.harmonycare.data.SharedPreferencesManager
import com.example.harmonycare.databinding.FragmentProfileBinding
import com.example.harmonycare.retrofit.ApiManager
import com.example.harmonycare.retrofit.ApiService
import com.example.harmonycare.retrofit.RetrofitClient

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val accessToken = SharedPreferencesManager.getAccessToken()

        if (!accessToken.isNullOrEmpty()) {
            val apiService = RetrofitClient.retrofit.create(ApiService::class.java)
            val apiManager = ApiManager(apiService)

            apiManager.getProfile(accessToken, onResponse = {
                if (it != null) {
                    binding.textParentName.text = it.parentName
                    binding.textEmail.text = it.email
                    binding.textBabyName.text = it.babyName
                    binding.textBirth.text = it.babyBirthDate
                }
            })
        }

        binding.myPost.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileMypost()
            findNavController().navigate(action)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}