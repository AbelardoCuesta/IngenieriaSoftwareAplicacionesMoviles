package com.example.vinilosmobile.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilosmobile.R
import com.example.vinilosmobile.databinding.MusicianItemBinding
import com.example.vinilosmobile.models.Musician

class MusiciansAdapter: RecyclerView.Adapter<MusiciansAdapter.MusicianViewHolder>() {

    var musicians: List<Musician> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicianViewHolder {
        val withDataBinding: MusicianItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            MusicianViewHolder.LAYOUT,
            parent,
            false)
        return MusicianViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: MusiciansAdapter.MusicianViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.musician = musicians[position]
        }
        /*holder.viewDataBinding.root.setOnClickListener {
            val action = AlbumFragmentDirections.actionAlbumFragmentToCommentFragment(albums[position].albumId)
            // Navigate using that action
            holder.viewDataBinding.root.findNavController().navigate(action)
        }*/
    }

    override fun getItemCount(): Int {
        return musicians.size
    }

    class MusicianViewHolder(val viewDataBinding: MusicianItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.musician_item
        }
    }

}