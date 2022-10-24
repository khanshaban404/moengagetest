package com.app.moengagetest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.moengagetest.R
import com.app.moengagetest.interfaces.ClickCallBacks
import com.app.moengagetest.model.NewsModel
import com.bumptech.glide.Glide


class NewsAdapter(
    private val context: Context,
    var newsList: ArrayList<NewsModel.Article>,
    private val clickCallBacks: ClickCallBacks
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewHeading.text = newsList[position].title
        holder.textViewDescription.text = newsList[position].description
        holder.textViewWriterName.text = newsList[position].author
        holder.textViewTime.text = newsList[position].publishedAt
        Glide.with(context).load(newsList[position].urlToImage).into(holder.imageViewPic)

        holder.textViewHeading.setOnClickListener{
            newsList[position].url?.let { it1 -> clickCallBacks.headingClick(it1) }
        }

        holder.imageViewMore.setOnClickListener {
            clickCallBacks.moreClick()
        }

        holder.imageViewShare.setOnClickListener {
            newsList[position].url?.let { it1 -> clickCallBacks.shareNews(it1) }
        }

        holder.imageViewLike.setOnClickListener {
            clickCallBacks.likeClick(holder.imageViewLike)
        }

        holder.imageViewBookmark.setOnClickListener {
            clickCallBacks.bookmarkClick(holder.imageViewBookmark)
        }


    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewHeading: TextView = itemView.findViewById(R.id.textViewHeading)
        val textViewTime: TextView = itemView.findViewById(R.id.textViewTime)
        val textViewWriterName: TextView = itemView.findViewById(R.id.textViewWriterName)
        val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        val imageViewPic: ImageView = itemView.findViewById(R.id.imageViewPic)
        val imageViewMore: ImageView = itemView.findViewById(R.id.imageViewMore)
        val imageViewShare: ImageView = itemView.findViewById(R.id.imageViewShare)
        val imageViewLike: ImageView = itemView.findViewById(R.id.imageViewLike)
        val imageViewBookmark: ImageView = itemView.findViewById(R.id.imageViewBookmark)
    }
}