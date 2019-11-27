package com.success.wiki

import android.app.Application
import com.success.wiki.managers.WikiManager
import com.success.wiki.providers.ArticleDataProvider
import com.success.wiki.repositories.ArticleDatabaseOpenHelper
import com.success.wiki.repositories.FavoritesRepository
import com.success.wiki.repositories.HistoryRepository

class WikiApplication: Application() {
   private var dbHelper: ArticleDatabaseOpenHelper? = null
   private var favoritesRepository: FavoritesRepository? = null
   private var historyRepository: HistoryRepository? = null
   private var wikiProvider: ArticleDataProvider? = null
   var wikiManager: WikiManager? = null
    private set


    override fun onCreate() {
        super.onCreate()

        dbHelper = ArticleDatabaseOpenHelper(applicationContext)
        favoritesRepository = FavoritesRepository(dbHelper!!)
        historyRepository = HistoryRepository(dbHelper!!)
        wikiProvider = ArticleDataProvider()
        wikiManager = WikiManager(wikiProvider!!, favoritesRepository!!, historyRepository!!)
    }
}