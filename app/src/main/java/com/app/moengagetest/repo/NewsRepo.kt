package com.app.moengagetest.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.moengagetest.model.NewsModel
import com.app.moengagetest.util.Constants
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class NewsRepo {

    private val news = MutableLiveData<List<NewsModel.Article>>()
    val newsData: LiveData<List<NewsModel.Article>>
        get() = news

    /**
     * get news list from web api and save it in livedata
     */
    suspend fun getAllNews() {
        try {
            val url = URL(Constants.URL)
            val connection = url.openConnection() as HttpsURLConnection
            if (connection.responseCode == 200) {
                val inputSystem = connection.inputStream
                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                Gson().fromJson(inputStreamReader, NewsModel::class.java).also {
                    news.postValue(it.articles)
                }
                inputStreamReader.close()
                inputSystem.close()
            } else {
             print("Something went to wrong")
            }
        }catch (e: Exception){
            print(e)
        }
    }
}