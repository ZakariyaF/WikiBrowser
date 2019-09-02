package com.zakariyaf.wikibrowser

import android.app.Application
import com.zakariyaf.wikibrowser.managers.WikiManager
import com.zakariyaf.wikibrowser.providers.ArticleDataProvider
import com.zakariyaf.wikibrowser.repositories.ArticleDBOpenHelper
import com.zakariyaf.wikibrowser.repositories.FavoritesRepository
import com.zakariyaf.wikibrowser.repositories.HistoryRepository

class WikiApplication : Application() {
    private var dbHelper: ArticleDBOpenHelper? = null
    private var favoritesRepository: FavoritesRepository? = null
    private var historyRepository: HistoryRepository? = null
    private var wikiProvider: ArticleDataProvider? = null
    private var wikiManager: WikiManager? = null

    override fun onCreate() {
        super.onCreate()
        dbHelper = ArticleDBOpenHelper(applicationContext)
        favoritesRepository = FavoritesRepository(dbHelper!!)
        historyRepository = HistoryRepository(dbHelper!!)
        wikiProvider = ArticleDataProvider()
        wikiManager = WikiManager(wikiProvider!!, favoritesRepository!!, historyRepository!!)
    }
}