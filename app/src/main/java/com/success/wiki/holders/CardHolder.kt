package com.success.wiki.holders


import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.success.wiki.R
import com.success.wiki.activities.ArticleDetailActivity
import com.success.wiki.models.WikiPage


class CardHolder (itemView: View): RecyclerView.ViewHolder(itemView){
    private val articleImageView: ImageView = itemView.findViewById(R.id.article_image)
    private val titleTextview: TextView = itemView.findViewById(R.id.article_title)

    private var currentPage: WikiPage? =  null
    init {
        itemView.setOnClickListener{view: View? ->
            var detailPageIntent = Intent(itemView.context, ArticleDetailActivity::class.java)
            var pageJson = Gson().toJson(currentPage)
            detailPageIntent.putExtra("page",pageJson)
            itemView.context.startActivity(detailPageIntent)
        }
    }

    fun updateWithPage(page: WikiPage){
        currentPage = page

        titleTextview.text = page.title

        //load images lazily with picasso
        if (page.thumbnail != null)
            Picasso.with(itemView.context).load(page.thumbnail!!.source).into(articleImageView)
    }
}