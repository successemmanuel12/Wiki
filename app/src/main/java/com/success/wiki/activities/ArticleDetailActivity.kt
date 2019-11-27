package com.success.wiki.activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.google.gson.Gson
import com.success.wiki.R
import com.success.wiki.WikiApplication
import com.success.wiki.managers.WikiManager
import com.success.wiki.models.WikiPage
import kotlinx.android.synthetic.main.activity_article_detail.*
import org.jetbrains.anko.toast
import java.lang.Exception

/**
 * created by Success on 10/10/2019
 */

class ArticleDetailActivity : AppCompatActivity() {

    private var wikiManager: WikiManager? = null
    private var currentPage: WikiPage? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)

        wikiManager = (applicationContext as WikiApplication).wikiManager

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //update toolbar title with page title
        supportActionBar?.title = currentPage?.title

        //get the page from the extras
        val wikiPageJson = intent.getStringExtra("page")
        currentPage = Gson().fromJson<WikiPage>(wikiPageJson, WikiPage::class.java)

        article_detail_webview.webChromeClient = object : WebChromeClient(){
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                page_loading_bar.progress = newProgress
            }
        }

        article_detail_webview.webViewClient = object: WebViewClient(){

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                page_loading_bar.visibility = View.VISIBLE
                page_loading_bar.progress = 0
                super.onPageStarted(view, url, favicon)

            }
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view?.loadUrl(request?.url.toString())
                }
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                page_loading_bar.visibility = View.GONE
            }
        }

        article_detail_webview.loadUrl(currentPage!!.fullurl)

        wikiManager?.addHistory(currentPage!!)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.article_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home){
            finish()
        }else if (item.itemId == R.id.action_favorite){
            //determine if article is favorites or not
            try {
                if (wikiManager!!.getIsFavorite(currentPage!!.pageid!!)){
                    wikiManager!!.removefavorite(currentPage!!.pageid!!)
                    toast("Article Removed From Favorites")
                }
                else{
                    wikiManager!!.addFavorites(currentPage!!)
                    toast("Article Added to Favorites")
                }
            }catch (ex: Exception){
                toast("unable to update article")
            }
        }
        return true
    }
}