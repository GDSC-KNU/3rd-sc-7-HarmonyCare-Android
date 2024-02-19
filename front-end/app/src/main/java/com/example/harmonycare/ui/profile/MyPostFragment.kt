package com.example.harmonycare.ui.profile

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.harmonycare.data.Post
import com.example.harmonycare.data.SharedPreferencesManager
import com.example.harmonycare.databinding.FragmentCommunityBinding
import com.example.harmonycare.retrofit.ApiManager
import com.example.harmonycare.retrofit.ApiService
import com.example.harmonycare.retrofit.RetrofitClient
import com.example.harmonycare.ui.community.CommunityFragmentDirections
import com.example.harmonycare.ui.community.PostAdapter

private var _binding: FragmentCommunityBinding? = null
private val binding get() = _binding!!
private lateinit var adapter: PostAdapter
class MyPostFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCommunityBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDataListAndSetAdapter()
    }

    private fun getDataList(onDataLoaded: (List<Post>) -> Unit) {
        val accessToken = SharedPreferencesManager.getAccessToken()

        if (!accessToken.isNullOrEmpty()) {
            val apiService = RetrofitClient.retrofit.create(ApiService::class.java)
            val apiManager = ApiManager(apiService)


            apiManager.getMyCommunity(accessToken,
                { myCommunityData ->
                    val sortedData = myCommunityData.sortedByDescending { it.communityId }
                    onDataLoaded(sortedData)
                }
            )
        }
    }

    private fun getDataListAndSetAdapter() {
        getDataList { communityData ->
            adapter = PostAdapter(communityData,
                onItemClick = { post ->
                    val action = MyPostFragmentDirections.actionNavigationProfileMypostToNavigationCommunityDetail(
                        communityId = post.communityId,
                        title = post.title,
                        content = post.content
                    )
                    findNavController().navigate(action)
                }
            )
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerView.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}