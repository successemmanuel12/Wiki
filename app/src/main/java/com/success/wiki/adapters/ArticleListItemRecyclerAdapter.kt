package com.success.wiki.adapters


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.success.wiki.R
import com.success.wiki.holders.ListItemHolder
import com.success.wiki.models.WikiPage

class ArticleListItemRecyclerAdapter() : RecyclerView.Adapter<ListItemHolder>() {
    val currentResults: ArrayList<WikiPage> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ListItemHolder {
    var cardItem = LayoutInflater.from(parent.context).inflate(R.layout.article_list_item, parent, false)

        return ListItemHolder(cardItem)
    }

    override fun getItemCount(): Int {
       return currentResults.size
    }

    override fun onBindViewHolder(holder: ListItemHolder, position: Int) {
        var page = currentResults[position]

        holder.updateWithPage(page)
    }
}