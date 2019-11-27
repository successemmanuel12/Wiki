package com.success.wiki.adapters


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.success.wiki.R
import com.success.wiki.holders.CardHolder
import com.success.wiki.models.WikiPage

class ArticleCardRecyclerAdapter() : RecyclerView.Adapter<CardHolder>() {
    val currentResults: ArrayList<WikiPage> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CardHolder {
    var cardItem = LayoutInflater.from(parent.context).inflate(R.layout.article_card_item, parent, false)

        return CardHolder(cardItem)
    }

    override fun getItemCount(): Int {
       return currentResults.size
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
       var page = currentResults[position]

        holder.updateWithPage(page)


    }
}