package com.zakariyaf.wikibrowser.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zakariyaf.wikibrowser.R
import com.zakariyaf.wikibrowser.holders.CardHolder
import com.zakariyaf.wikibrowser.holders.ListItemHolder

class ArticleListItemRecyclerAdapter() : RecyclerView.Adapter<ListItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemHolder {
        var cardItem = LayoutInflater.from(parent?.context).inflate(R.layout.article_list_item, parent, false)
        return ListItemHolder(cardItem)
    }

    override fun getItemCount(): Int {
        return 10 //TODO change this
    }

    override fun onBindViewHolder(holder: ListItemHolder, position: Int) {

    }
}