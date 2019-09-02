package com.zakariyaf.wikibrowser.repositories

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class ArticleDBOpenHelper(context: Context) :
    ManagedSQLiteOpenHelper(context, "ArticlesDatabase.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(
            "Favorites", true,
            "id" to INTEGER + PRIMARY_KEY,
            "title" to TEXT,
            "url" to TEXT,
            "thumbnailJson" to TEXT
        )
        db?.createTable(
            "History", true,
            "id" to INTEGER + PRIMARY_KEY,
            "title" to TEXT,
            "url" to TEXT,
            "thumbnailJson" to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}