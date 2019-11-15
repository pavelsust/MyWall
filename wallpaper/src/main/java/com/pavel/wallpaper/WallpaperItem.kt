package com.wallpaper

import androidx.annotation.ColorInt
import com.nostra13.universalimageloader.core.assist.ImageSize

data class WallpaperItem(var imageLink: String) {
    var mimeType: String? = null
    //var imageSize: Int? = null

    @ColorInt
    var color : Int?=null
    var imageDimension: ImageSize? = null
}