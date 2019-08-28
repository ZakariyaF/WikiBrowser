package com.zakariyaf.wikibrowser.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zakariyaf.wikibrowser.R
import com.zakariyaf.wikibrowser.models.WikiPage

class ListItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val articleImageView: ImageView = itemView.findViewById(R.id.result_icon)
    private val titleTextView: TextView = itemView.findViewById(R.id.result_title)

    private var currentPage: WikiPage? = null

    fun updateWithPage(page: WikiPage) {
        if (page.thumbnail != null)
            Picasso.get().load(page.thumbnail!!.source).into(articleImageView)
        titleTextView.text = page.title
        currentPage = page
    }
}