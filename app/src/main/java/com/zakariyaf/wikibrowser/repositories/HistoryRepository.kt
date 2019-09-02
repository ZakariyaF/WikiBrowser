package com.zakariyaf.wikibrowser.repositories

import com.google.gson.Gson
import com.zakariyaf.wikibrowser.models.WikiPage
import com.zakariyaf.wikibrowser.models.WikiThumbnail
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.rowParser

class HistoryRepository(val databaseHelper: ArticleDBOpenHelper) {
    private val TABLE_NAME: String = "History"

    fun addHistory(page: WikiPage) {
        databaseHelper.use {
            insert(
                TABLE_NAME,
                "id" to page.pageid,
                "title" to page.title,
                "url" to page.fullurl,
                "thumbnailJson" to Gson().toJson(page.thumbnail)
            )
        }
    }

    fun removeHistoryPageById(pageId: Int) {
        databaseHelper.use {
            delete(
                TABLE_NAME,
                "id = {pageid}", "pageid" to pageId
            )
        }
    }

    fun getAllHistory(): ArrayList<WikiPage> {
        var pages = ArrayList<WikiPage>()
        val articleRowParser = rowParser { id: Int, title: String, url: String, thumbnailJson: String ->
            val page = WikiPage()
            page.title = title
            page.pageid = id
            page.fullurl = url
            page.thumbnail = Gson().fromJson(thumbnailJson, WikiThumbnail::class.java)
            pages.add(page)
        }
        return pages
    }
}