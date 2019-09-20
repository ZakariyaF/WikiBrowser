package com.zakariyaf.wikibrowser.activities

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.zakariyaf.wikibrowser.R
import com.zakariyaf.wikibrowser.WikiApplication
import com.zakariyaf.wikibrowser.helpers.ConnectivityHelper
import com.zakariyaf.wikibrowser.managers.WikiManager
import com.zakariyaf.wikibrowser.models.WikiPage
import kotlinx.android.synthetic.main.activity_article_detail.*
import org.jetbrains.anko.toast

class ArticleDetailActivity : AppCompatActivity() {

    private var wikiManager: WikiManager? = null
    private var currentPage: WikiPage? = null
    private var noConnectionTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        wikiManager = (applicationContext as WikiApplication).wikiManager

        noConnectionTextView = findViewById(R.id.noConnectionTextViewDetail)

        val wikiPageJson = intent.getStringExtra("page")
        currentPage = Gson().fromJson<WikiPage>(wikiPageJson, WikiPage::class.java)

        supportActionBar?.title = currentPage?.title



        article_detail_webview?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                page_progress_bar?.visibility = View.VISIBLE
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                page_progress_bar?.visibility = View.INVISIBLE
                super.onPageFinished(view, url)
            }
        }
        article_detail_webview.settings.javaScriptEnabled = true
        loadWikiPage()

    }

    private fun loadWikiPage() {
        ConnectivityHelper.hasInternetConnection().subscribe { hasInternet ->
            if (hasInternet) {
                noConnectionTextView?.visibility = View.INVISIBLE
                article_detail_webview.loadUrl(currentPage!!.fullurl)

                wikiManager?.addHistory(currentPage!!)
            } else {
                noConnectionTextView?.visibility = View.VISIBLE
                page_progress_bar?.visibility = View.INVISIBLE
                var snackbar =
                    Snackbar.make(noConnectionTextView!!, "No connection. Try again?", Snackbar.LENGTH_INDEFINITE)
                snackbar.setAction("Retry!") { loadWikiPage() }
                snackbar.show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.article_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item!!.itemId == android.R.id.home) {
            finish()
        }
        ConnectivityHelper.hasInternetConnection().subscribe { hasInternet ->
            if (hasInternet) {
                if (item.itemId == R.id.action_favorite) {
                    try {
                        if (wikiManager!!.getIsFavorite(currentPage!!.pageid!!)) {
                            wikiManager!!.removeFavorite(currentPage!!.pageid!!)
                            toast("Article removed from favorites")
                        } else {
                            wikiManager!!.addFavorite(currentPage!!)
                            toast("Article added to favorites")
                        }
                    } catch (ex: Exception) {
                        toast("Unable to update this article!")
                    }
                }
            } else {
                if (item.itemId == R.id.action_favorite) {
                    toast("No connection.")
                }
            }
        }

        return true
    }
}
