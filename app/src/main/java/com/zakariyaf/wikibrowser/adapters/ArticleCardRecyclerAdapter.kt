package com.zakariyaf.wikibrowser.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zakariyaf.wikibrowser.R
import com.zakariyaf.wikibrowser.holders.CardHolder
import com.zakariyaf.wikibrowser.models.WikiPage

class ArticleCardRecyclerAdapter() : RecyclerView.Adapter<CardHolder>() {

    val currentResults: ArrayList<WikiPage> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        var cardItem = LayoutInflater.from(parent?.context).inflate(R.layout.article_card_item, parent, false)
        return CardHolder(cardItem)
    }

    override fun getItemCount(): Int {
        return currentResults.size
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        var page = currentResults[position]
        holder.updateWithPage(page)
    }
}