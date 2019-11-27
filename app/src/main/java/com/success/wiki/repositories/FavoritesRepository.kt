package com.success.wiki.repositories

import com.google.gson.Gson
import com.success.wiki.models.WikiPage
import com.success.wiki.models.WikiThumbnail
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.db.select
import org.xml.sax.Parser

class FavoritesRepository(val databaseHelper: ArticleDatabaseOpenHelper) {
    private val TABLE_NAME: String = "favorites"

    fun addFavorites(page: WikiPage) {
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

    fun removeFavoritesById(pageId: Int){
        databaseHelper.use {
            delete(TABLE_NAME,
                "id = {pageId}",  "pageId" to pageId)
        }
    }

    fun isArticleFavorite(pageId: Int) : Boolean{
        //get favorites and filter
        var pages = getAllFavorites()
        return  pages.any{ pages->
            pages.pageid == pageId
        }
    }

    fun getAllFavorites(): ArrayList<WikiPage>{
        var pages = ArrayList<WikiPage>()

        val articleRowParser =  rowParser{id: Int, title: String, url: String, thumbnailJson: String ->
            val page = WikiPage()
            page.title = title
            page.pageid = id
            page.fullurl = url
            page.thumbnail = Gson().fromJson(thumbnailJson, WikiThumbnail::class.java)

            pages.add(page)
        }
        databaseHelper.use {
            select(TABLE_NAME).parseList(articleRowParser)
        }

        return pages
    }
}