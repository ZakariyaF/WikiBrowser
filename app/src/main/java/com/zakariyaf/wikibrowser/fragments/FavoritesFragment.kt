package com.zakariyaf.wikibrowser.fragments


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.zakariyaf.wikibrowser.R
import com.zakariyaf.wikibrowser.WikiApplication
import com.zakariyaf.wikibrowser.adapters.ArticleCardRecyclerAdapter
import com.zakariyaf.wikibrowser.managers.WikiManager
import com.zakariyaf.wikibrowser.models.WikiPage
import org.jetbrains.anko.doAsync

/**
 * A simple [Fragment] subclass.
 *
 */
class FavoritesFragment : Fragment() {

    private var wikiManager: WikiManager? = null
    var favoritesRecycler: RecyclerView? = null
    var noFav_textview: TextView? = null

    private val adapter: ArticleCardRecyclerAdapter = ArticleCardRecyclerAdapter()

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        wikiManager = (activity?.applicationContext as WikiApplication).wikiManager
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)
        favoritesRecycler = view.findViewById(R.id.favorites_article_recycler)
        noFav_textview = view.findViewById(R.id.noFavTextView)
        favoritesRecycler!!.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        favoritesRecycler!!.adapter = adapter

        return view
    }

    override fun onResume() {
        super.onResume()

        doAsync {
            val favoriteArticles = wikiManager!!.getFavorites()
            if (favoriteArticles!!.isEmpty()) {
                favoritesRecycler?.visibility = View.INVISIBLE
                noFav_textview?.visibility = View.VISIBLE
            } else {
                favoritesRecycler?.visibility = View.VISIBLE
                adapter.currentResults.clear()
                adapter.currentResults.addAll(favoriteArticles as ArrayList<WikiPage>)
                activity?.runOnUiThread { adapter.notifyDataSetChanged() }
            }
        }
    }

}
