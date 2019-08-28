package com.zakariyaf.wikibrowser.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zakariyaf.wikibrowser.R
import com.zakariyaf.wikibrowser.holders.CardHolder
import com.zakariyaf.wikibrowser.holders.ListItemHolder
import com.zakariyaf.wikibrowser.models.WikiPage

class ArticleListItemRecyclerAdapter() : RecyclerView.Adapter<ListItemHolder>() {

    val currentResults: ArrayList<WikiPage> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemHolder {
        var cardItem = LayoutInflater.from(parent?.context).inflate(R.layout.article_list_item, parent, false)
        return ListItemHolder(cardItem)
    }

    override fun getItemCount(): Int {
        return currentResults.size
    }

    override fun onBindViewHolder(holder: ListItemHolder, position: Int) {
        var page = currentResults[position]
        holder.updateWithPage(page)
    }
}