package com.app.moengagetest.interfaces

import android.widget.ImageView

/**
 * Callback interface and methods
 */
interface ClickCallBacks {
    fun moreClick()
    fun headingClick(url: String)
    fun shareNews(url: String)
    fun likeClick(imageViewLike: ImageView)
    fun bookmarkClick(imageViewLike: ImageView)
}