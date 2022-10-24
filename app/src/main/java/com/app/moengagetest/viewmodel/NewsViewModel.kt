package com.app.moengagetest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.moengagetest.model.NewsModel
import com.app.moengagetest.repo.NewsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel(){
    // initialise the Repository
    private var newsRepo: NewsRepo = NewsRepo()

    val fetchNewsListData : LiveData<List<NewsModel.Article>>
        get() = newsRepo.newsData

    /**
     * launch the coroutine to get all news data.
     */
    init {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepo.getAllNews()
        }
    }
}