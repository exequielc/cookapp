package com.duo.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.duo.myapplication.databinding.AdapterItemBinding
import com.duo.myapplication.fragments.placeholder.PlaceholderContent


class ItemSimpleAdapter(
    private val values: List<PlaceholderContent.PlaceholderItem>
) : RecyclerView.Adapter<ItemSimpleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            AdapterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.id
        holder.contentView.text = item.content
        holder.detailView.text = item.details
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: AdapterItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNum
        val contentView: TextView = binding.itemVal
        val detailView: TextView = binding.itemText
        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }



}