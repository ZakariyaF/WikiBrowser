package com.zakariyaf.wikibrowser.repositories

import com.google.gson.Gson
import com.zakariyaf.wikibrowser.models.WikiPage
import com.zakariyaf.wikibrowser.models.WikiThumbnail
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.rowParser

class FavoritesRepository(val databaseHelper: ArticleDBOpenHelper) {
    private val TABLE_NAME: String = "Favorites"

    fun addFavorite(page: WikiPage) {
        databaseHelper.use {
            insert(
                TABLE_NAME,
                "id" to page.pageId,
                "title" to page.title,
                "url" to page.fullurl,
                "thumbnailJson" to Gson().toJson(page.thumbnail)
            )
        }
    }

    fun removeFavoriteById(pageId: Int) {
        databaseHelper.use {
            delete(
                TABLE_NAME,
                "id = {pageId}", "pageId" to pageId
            )
        }
    }

    fun isArticleFavorite(pageId: Int): Boolean {
        var pages = getAllFavorites()
        return pages.any { page ->
            page.pageId == pageId
        }
    }

    fun getAllFavorites(): ArrayList<WikiPage> {
        var pages = ArrayList<WikiPage>()
        val articleRowParser = rowParser { id: Int, title: String, url: String, thumbnailJson: String ->
            val page = WikiPage()
            page.title = title
            page.pageId = id
            page.fullurl = url
            page.thumbnail = Gson().fromJson(thumbnailJson, WikiThumbnail::class.java)
            pages.add(page)
        }
        return pages
    }
}