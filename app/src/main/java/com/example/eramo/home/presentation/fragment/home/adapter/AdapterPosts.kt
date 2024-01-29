package com.example.eramo.home.presentation.fragment.home.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.eramo.core.presentation.extensions.layoutInflater
import com.example.eramo.databinding.LayoutPostsBinding
import com.example.eramo.home.data.responseremote.getPost.ModelGetPostsResponseRemoteItem

class AdapterPosts(
    private val itemSelected: (String) -> Unit,
) :
    ListAdapter<ModelGetPostsResponseRemoteItem, RecyclerView.ViewHolder>(
        diffCallback
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding =
            LayoutPostsBinding.inflate(parent.layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {

        getItem(position)?.let {
            (holder as ViewHolder).bind(it)

        }
    }

    private inner class ViewHolder(private val binding: LayoutPostsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.M)
        fun bind(item: ModelGetPostsResponseRemoteItem) = with(binding) {
            cardPost.setOnClickListener {
                itemSelected("${item.id}")
            }

            tvTitleValue.text = item.title
            tvBodyValue.text = item.body
        }

    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<ModelGetPostsResponseRemoteItem>() {
            override fun areItemsTheSame(
                oldItem: ModelGetPostsResponseRemoteItem,
                newItem: ModelGetPostsResponseRemoteItem
            ): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: ModelGetPostsResponseRemoteItem,
                newItem: ModelGetPostsResponseRemoteItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}