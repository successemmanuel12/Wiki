package com.success.wiki.managers

import com.success.wiki.models.WikiPage
import com.success.wiki.models.WikiResult
import com.success.wiki.providers.ArticleDataProvider
import com.success.wiki.repositories.FavoritesRepository
import com.success.wiki.repositories.HistoryRepository

class WikiManager(
    private val provider: ArticleDataProvider,
    private val favoritesRepository: FavoritesRepository,
    private val historyRepository: HistoryRepository
) {

    var favoriteCache: ArrayList<WikiPage>? = null
    var historyCache: ArrayList<WikiPage>? = null

    fun search(term: String, skip: Int, take: Int, handler: (result: WikiResult) -> Unit?) {

        return provider.search(term, skip, take, handler)
    }

    fun getRandom(take: Int, handler: (result: WikiResult) -> Unit?) {

        return provider.getRandom(take, handler)
    }

    fun getHistory(): ArrayList<WikiPage>? {
        if (historyCache == null)
            historyCache = historyRepository.getAllHistory()
        return historyCache
    }

    fun getFavorites(): ArrayList<WikiPage>? {
        if (favoriteCache == null)
            favoriteCache = favoritesRepository.getAllFavorites()
        return favoriteCache
    }

    fun addFavorites(page: WikiPage) {
        favoriteCache?.add(page)
        favoritesRepository.addFavorites(page)
    }

    fun removefavorite(pageId: Int) {
        favoritesRepository.removeFavoritesById(pageId)
        favoriteCache = favoriteCache!!.filter { it.pageid != pageId } as ArrayList<WikiPage>
    }

    fun getIsFavorite(pageId: Int): Boolean {
        return favoritesRepository.isArticleFavorite(pageId)
    }

    fun addHistory(page: WikiPage) {
        historyCache?.add(page)
        historyRepository.addHistory(page)
    }

    fun clearHistory() {
       historyCache?.clear()
       val allHistory = historyRepository.getAllHistory()
       allHistory.forEach{historyRepository.removePageById(it.pageid!!)}
    }
}