package com.zakariyaf.wikibrowser.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zakariyaf.wikibrowser.R
import com.zakariyaf.wikibrowser.holders.CardHolder

class ArticleCardRecyclerAdapter() : RecyclerView.Adapter<CardHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        var cardItem = LayoutInflater.from(parent?.context).inflate(R.layout.article_card_item, parent, false)
        return CardHolder(cardItem)
    }

    override fun getItemCount(): Int {
        return 10 //TODO change this
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {

    }
}