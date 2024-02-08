package com.example.harmonycare.ui.community

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.harmonycare.data.Post
import com.example.harmonycare.databinding.CommunityItemBinding

class PostAdapter(private val dataList: List<Post>, private val onItemClick: (Post) -> Unit) : RecyclerView.Adapter<PostAdapter.PostViewHolder>()  {
    inner class PostViewHolder(private val binding: CommunityItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val post = dataList[position]
                    onItemClick(post)
                }
            }
        }
        fun bind(post: Post) {
            binding.textTitle.text = post.title
            binding.textCaption.text = post.caption
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.PostViewHolder {
        val binding = CommunityItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostAdapter.PostViewHolder, position: Int) {
        val post = dataList[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}