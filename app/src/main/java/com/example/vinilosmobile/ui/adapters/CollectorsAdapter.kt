package com.example.vinilosmobile.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilosmobile.R
import com.example.vinilosmobile.databinding.AlbumItemBinding
import com.example.vinilosmobile.databinding.CollectorItemBinding
import com.example.vinilosmobile.models.Collector
import com.example.vinilosmobile.ui.album.AlbumFragmentDirections
import com.example.vinilosmobile.ui.collector.CollectorFragmentDirections
import com.squareup.picasso.Picasso
class CollectorsAdapter: RecyclerView.Adapter<CollectorsAdapter.CollectorViewHolder>() {

    var collectors: List<Collector> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorsAdapter.CollectorViewHolder {
        val withDataBinding: CollectorItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CollectorsAdapter.CollectorViewHolder.LAYOUT,
            parent,
            false)
        return CollectorsAdapter.CollectorViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: CollectorViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.collector = collectors[position]
        }
        holder.viewDataBinding.root.setOnClickListener {
            val action = CollectorFragmentDirections.actionNavCollectorsToNavDetailCollector(collectors[position].collectorId)
            // Navigate using that action
            holder.viewDataBinding.root.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return collectors.size
    }

    class CollectorViewHolder(val viewDataBinding: CollectorItemBinding):
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.collector_item
        }
    }
}