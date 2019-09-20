package com.zakariyaf.wikibrowser.activities

import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.zakariyaf.wikibrowser.R
import com.zakariyaf.wikibrowser.WikiApplication
import com.zakariyaf.wikibrowser.adapters.ArticleListItemRecyclerAdapter
import com.zakariyaf.wikibrowser.helpers.ConnectivityHelper
import com.zakariyaf.wikibrowser.managers.WikiManager
import com.zakariyaf.wikibrowser.providers.ArticleDataProvider
import kotlinx.android.synthetic.main.activity_article_detail.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.toolbar
import org.jetbrains.anko.toast

class SearchActivity : AppCompatActivity() {

    private var wikiManager: WikiManager? = null
    private var adapter: ArticleListItemRecyclerAdapter = ArticleListItemRecyclerAdapter()

    private var noResultsTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        noResultsTextView = findViewById(R.id.noResultsTextView)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        wikiManager = (applicationContext as WikiApplication).wikiManager

        search_results_recycler.layoutManager = LinearLayoutManager(this)
        search_results_recycler.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item!!.itemId == android.R.id.home) {
            finish()
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu!!.findItem(R.id.action_search)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = searchItem!!.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setIconifiedByDefault(false)
        searchView.requestFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                ConnectivityHelper.hasInternetConnection().subscribe { hasInternet ->
                    if (hasInternet) {
                        wikiManager?.search(query, 0, 20) { wikiResult ->
                            adapter.currentResults.clear()
                            wikiResult.query?.pages?.let { adapter.currentResults.addAll(it) }
                            runOnUiThread {
                                adapter.notifyDataSetChanged()
                                if (adapter.currentResults.isEmpty()) {
                                    noResultsTextView?.visibility = View.VISIBLE
                                } else {
                                    noResultsTextView?.visibility = View.INVISIBLE
                                }
                            }
                        }

                    } else {
                        adapter.currentResults.clear()
                        var snackbar = Snackbar.make(
                            noResultsTextView!!!!,
                            "No connection. Try again?",
                            Snackbar.LENGTH_INDEFINITE
                        )
                        snackbar.setAction("Retry!") { onQueryTextSubmit(query) }
                        snackbar.show()
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}
