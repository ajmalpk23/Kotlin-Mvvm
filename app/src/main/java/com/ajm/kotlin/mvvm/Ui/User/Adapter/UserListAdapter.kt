package com.ajm.kotlin.mvvm.Ui.User.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ajm.kotlin.mvvm.Data.Remote.Response.UserApiResponse.DataItem
import com.ajm.kotlin.mvvm.Utils.ExtensionFunctions.loadUrl
import com.ajm.kotlin.mvvm.databinding.ItemListItemBinding

class UserListAdapter(private val onItemClick: (DataItem) -> Unit,private val onDelete: (DataItem) -> Unit) :
    ListAdapter<DataItem, UserListAdapter.ViewHolder>(ItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: ItemListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SuspiciousIndentation", "SetTextI18n")
        fun bind(item: DataItem) {
            binding.textViewName.text = item.firstName+" "+item.lastName
            binding.txtEmail.text = item.email
        binding.imageView.isVisible = false
        binding.txtLable.isVisible = false
//            if (adapterPosition % 2 == 0) {
//                binding.imageView.isVisible = true
//                binding.imageView.loadUrl(item.avatar)
//            } else {
//                binding.txtLable.isVisible = true
//                binding.txtLable.text = item.firstName?.get(0).toString()+item.lastName?.get(0).toString()
//
//            }
            if ((item.id?.rem(2) ?: 0) == 0) {

                binding.txtLable.isVisible = true
                var name ="";
                if(item.firstName?.length!! >0){
                    name = item.firstName?.get(0).toString()
                }
                if(item.lastName?.length!! >0){
                    name =name.plus( item.lastName?.get(0).toString())
                }
                binding.txtLable.text = name
            } else {
                binding.imageView.isVisible = true
                binding.imageView.loadUrl(item.avatar)

            }
            binding.root.setOnClickListener {
                onItemClick(item)
            }
            binding.imgDelete.setOnClickListener(View.OnClickListener {
                onDelete(item)
            })
        }
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }
    }
}