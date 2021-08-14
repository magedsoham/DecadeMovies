package com.maged.oldmovies.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maged.oldmovies.R
import com.maged.oldmovies.utils.Utils.Companion.runWithCaution

/**
 * Adapter used to display each movie pics
 */
class MoviesPicsRecyclerViewAdapter(

    private val items: List<String>

) : RecyclerView.Adapter<MoviesPicsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_photo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        runWithCaution({
            Glide.with(holder.itemView.context)
                .load(item)
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.picIV)
        })
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val picIV: ImageView = view.findViewById(R.id.iv_pic)
    }
}