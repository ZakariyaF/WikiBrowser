package com.zakariyaf.wikibrowser.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.zakariyaf.wikibrowser.R
import com.zakariyaf.wikibrowser.adapters.ArticleCardRecyclerAdapter
import com.zakariyaf.wikibrowser.adapters.ArticleListItemRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_favorites.*

/**
 * A simple [Fragment] subclass.
 *
 */
class FavoritesFragment : Fragment() {

    var favoritesRecycler: RecyclerView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)
        favoritesRecycler = view.findViewById(R.id.favorites_article_recycler)
        favoritesRecycler!!.layoutManager = LinearLayoutManager(context)
        favoritesRecycler!!.adapter = ArticleCardRecyclerAdapter()

        return view
    }


}
