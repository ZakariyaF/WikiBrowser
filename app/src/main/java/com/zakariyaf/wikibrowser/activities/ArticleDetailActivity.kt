package com.zakariyaf.wikibrowser.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.google.gson.Gson
import com.zakariyaf.wikibrowser.R
import com.zakariyaf.wikibrowser.WikiApplication
import com.zakariyaf.wikibrowser.managers.WikiManager
import com.zakariyaf.wikibrowser.models.WikiPage
import kotlinx.android.synthetic.main.activity_article_detail.*
import org.jetbrains.anko.toast

class ArticleDetailActivity : AppCompatActivity() {

    private var wikiManager: WikiManager? = null
    private var currentPage: WikiPage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        wikiManager = (applicationContext as WikiApplication).wikiManager

        val wikiPageJson = intent.getStringExtra("page")
        currentPage = Gson().fromJson<WikiPage>(wikiPageJson, WikiPage::class.java)

        supportActionBar?.title = currentPage?.title

        article_detail_webview?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return true
            }
        }
        article_detail_webview.loadUrl(currentPage!!.fullurl)

        wikiManager?.addHistory(currentPage!!)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.article_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item!!.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.action_favorite) {
            try {
                if (wikiManager!!.getIsFavorite(currentPage!!.pageId!!)) {
                    wikiManager!!.removeFavorite(currentPage!!.pageId!!)
                    toast("Article removed from favorites")
                } else {
                    wikiManager!!.addFavorite(currentPage!!)
                    toast("Article added to favorites")
                }
            } catch (ex: Exception) {
                toast("Unable to update this article!")
            }
        }
        return true
    }
}
