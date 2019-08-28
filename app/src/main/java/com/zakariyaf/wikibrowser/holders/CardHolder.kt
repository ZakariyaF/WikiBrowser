package com.zakariyaf.wikibrowser.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zakariyaf.wikibrowser.R
import com.zakariyaf.wikibrowser.models.WikiPage

class CardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val articleImageView: ImageView = itemView.findViewById(R.id.article_image)
    private val titleTextView: TextView = itemView.findViewById(R.id.article_title)

    private var currentPage: WikiPage? = null

    fun updateWithPage(page: WikiPage) {
        currentPage = page
        titleTextView.text = page.title

        if (page.thumbnail != null) {
            Picasso.get().load(page.thumbnail!!.source).into(articleImageView)
        }

    }
}