package com.zakariyaf.wikibrowser.fragments


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.zakariyaf.wikibrowser.R
import com.zakariyaf.wikibrowser.activities.SearchActivity
import com.zakariyaf.wikibrowser.adapters.ArticleCardRecyclerAdapter
import com.zakariyaf.wikibrowser.providers.ArticleDataProvider
import kotlinx.android.synthetic.main.fragment_explore.*
import java.lang.Exception


/**
 * A simple [Fragment] subclass.
 *
 */
class ExploreFragment : Fragment() {

    private val articleProvider: ArticleDataProvider = ArticleDataProvider()

    var searchCardView: CardView? = null
    var exploreRecycler: RecyclerView? = null
    var adapter: ArticleCardRecyclerAdapter = ArticleCardRecyclerAdapter()
    var refresher: SwipeRefreshLayout? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_explore, container, false)
        refresher = view.findViewById(R.id.refresher)
        searchCardView = view.findViewById(R.id.search_card_view)
        exploreRecycler = view.findViewById(R.id.explore_article_recycler)
        searchCardView!!.setOnClickListener {
            val searchIntent = Intent(context, SearchActivity::class.java)
            context!!.startActivity(searchIntent)
        }
        exploreRecycler!!.layoutManager = LinearLayoutManager(context)
        exploreRecycler!!.adapter = adapter

        refresher?.setOnRefreshListener {
            getRandomArticles()
        }
        getRandomArticles()
        return view
    }

    private fun getRandomArticles() {
        refresher?.isRefreshing = true
        try {
            articleProvider.getRandom(15, { wikiResult ->
                adapter.currentResults.clear()
                adapter.currentResults.addAll(wikiResult.query!!.pages)
                activity?.runOnUiThread {
                    adapter.notifyDataSetChanged()
                    refresher?.isRefreshing = false
                }
            })
        } catch (ex: Exception) {
            val builder = AlertDialog.Builder(activity)
            builder.setMessage(ex.message).setTitle("Oops!")
            val dialog = builder.create()
            dialog.show()
        }

    }

}
