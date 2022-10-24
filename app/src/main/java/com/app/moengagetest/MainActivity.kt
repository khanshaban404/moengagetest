package com.app.moengagetest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.moengagetest.adapter.NewsAdapter
import com.app.moengagetest.databinding.ActivityMainBinding
import com.app.moengagetest.interfaces.ClickCallBacks
import com.app.moengagetest.model.NewsModel
import com.app.moengagetest.util.Constants
import com.app.moengagetest.viewmodel.NewsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ClickCallBacks {
    private lateinit var binding: ActivityMainBinding
    lateinit var newsAdapter: NewsAdapter
    private var newsList = ArrayList<NewsModel.Article>()
    lateinit var newsViewModel: NewsViewModel
    private var isBookmark: Boolean = false
    private var isLiked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initData()
    }

    /**
     * initialize viewmodel and fetch news data from viewmodel
     * using fetchNewsListData method
     */
    private fun initData() {

        newsViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[NewsViewModel::class.java]

        if (Constants.isOnline(this)) {
            newsViewModel.fetchNewsListData.observe(this, Observer {
                if (it.isNotEmpty()) {
                    Log.d("data", "" + it)
                    newsList.addAll(it)
                    updateUI(newsList)
                    binding.progressBar.visibility = View.INVISIBLE
                } else {
                    Toast.makeText(this, "Something went to wrong", Toast.LENGTH_SHORT).show()
                }
            })
        }else{
            binding.progressBar.visibility = View.INVISIBLE
            binding.imageViewNoInternet.visibility = View.VISIBLE
        }
    }

    /**
     * Update the UI according to list data
     * and update the adapter
     */
    private fun updateUI(list: ArrayList<NewsModel.Article>) {
        runOnUiThread {
            kotlin.run {
                newsAdapter = NewsAdapter(this, list, this)
                val layout = LinearLayoutManager(this)
                recyclerView.adapter = newsAdapter
                recyclerView.layoutManager = layout
                newsAdapter.notifyDataSetChanged()
            }
        }
    }

    /**
     * more click callback to open
     * bottom sheet for more option on news
     */
    override fun moreClick() {
        val view = LayoutInflater.from(this).inflate(R.layout.more_bottom_sheet, null)
        val dialog = BottomSheetDialog(this, R.style.SheetDialog)
        dialog.setContentView(view)
        dialog.show()
    }

    /**
     * Click heading callback to open news details in browser
     */
    override fun headingClick(url: String) {
        if ((url.startsWith("http://") || url.startsWith("https://")) && url.isNotEmpty()) {
            val intent = Intent(this, NewsDetailsActivity::class.java)
            intent.putExtra("url", url)
            startActivity(intent)
        } else {
            Toast.makeText(this, "News is not found", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Share click callback to Share news via whatsapp
     * and other social media platform
     */
    override fun shareNews(url: String) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, url);
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_via)))
    }

    /**
     * Click Like icon callback to like the news
     */
    override fun likeClick(imageViewLike: ImageView) {
        isLiked = if (!isLiked) {
            imageViewLike.setColorFilter(ContextCompat.getColor(this, R.color.red))
            true
        } else {
            imageViewLike.setColorFilter(ContextCompat.getColor(this, R.color.gray))
            false
        }
    }

    /**
     * Click bookmark icon callback to save the news
     */
    override fun bookmarkClick(imageViewLike: ImageView) {
        isBookmark = if (!isBookmark) {
            imageViewLike.setImageDrawable(getDrawable(R.drawable.ic_bookmarked))
            true
        } else {
            imageViewLike.setImageDrawable(getDrawable(R.drawable.ic_bookmark))
            false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    /**
     * menu option for sort the news list according to
     * new to old and
     * old to new
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.newToOld -> {
                if (Constants.isOnline(this)) {
                    val reversed = newsList.reversed()
                    updateUI(reversed as ArrayList<NewsModel.Article>)
                }
                true
            }
            R.id.OldToNew -> {
                if (Constants.isOnline(this)) {
                    updateUI(newsList)
                }
                    return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}


