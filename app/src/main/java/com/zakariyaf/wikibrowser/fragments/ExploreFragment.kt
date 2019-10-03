package com.zakariyaf.wikibrowser.fragments


import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.zakariyaf.wikibrowser.R
import com.zakariyaf.wikibrowser.WikiApplication
import com.zakariyaf.wikibrowser.activities.SearchActivity
import com.zakariyaf.wikibrowser.adapters.ArticleCardRecyclerAdapter
import com.zakariyaf.wikibrowser.helpers.ConnectivityHelper
import com.zakariyaf.wikibrowser.managers.WikiManager


/**
 * A simple [Fragment] subclass.
 *
 */
class ExploreFragment : Fragment() {

    private var wikiManager: WikiManager? = null

    var searchCardView: CardView? = null
    var exploreRecycler: RecyclerView? = null
    var adapter: ArticleCardRecyclerAdapter = ArticleCardRecyclerAdapter()
    var refresher: SwipeRefreshLayout? = null
    var noConnectionTextView: TextView? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        wikiManager = (activity?.applicationContext as WikiApplication).wikiManager
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_explore, container, false)
        refresher = view.findViewById(R.id.refresher)
        searchCardView = view.findViewById(R.id.search_card_view)
        exploreRecycler = view.findViewById(R.id.explore_article_recycler)
        noConnectionTextView = view.findViewById(R.id.noConnectionTextView)
        searchCardView!!.setOnClickListener {
            val searchIntent = Intent(context, SearchActivity::class.java)
            context!!.startActivity(searchIntent)
        }
        exploreRecycler!!.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        exploreRecycler!!.adapter = adapter

        refresher?.setOnRefreshListener {
            getRandomArticles()
        }
        getRandomArticles()
        return view
    }

    private fun getRandomArticles() {
        ConnectivityHelper.hasInternetConnection().subscribe { hasInternet ->
            if (hasInternet) {
                refresher?.isRefreshing = true
                try {
                    wikiManager?.getRandom(15) { wikiResult ->
                        adapter.currentResults.clear()
                        adapter.currentResults.addAll(wikiResult.query!!.pages)
                        refresher?.isRefreshing = false
                        activity?.runOnUiThread {
                            noConnectionTextView?.visibility = View.INVISIBLE
                            adapter.notifyDataSetChanged()
                        }
                    }
                } catch (ex: Exception) {
                    val builder = AlertDialog.Builder(activity)
                    builder.setMessage(ex.message).setTitle("Oops!")
                    val dialog = builder.create()
                    dialog.show()
                }
            } else {
                adapter.currentResults.clear()
                refresher?.isRefreshing = false
                activity?.runOnUiThread {
                    noConnectionTextView?.visibility = View.VISIBLE
                    var snackbar =
                        Snackbar.make(
                            exploreRecycler!!,
                            "No connection. Try again?",
                            Snackbar.LENGTH_INDEFINITE
                        )
                    snackbar.setAction("Retry!") { getRandomArticles() }
                    snackbar.show()
                }
            }
        }

    }

}
