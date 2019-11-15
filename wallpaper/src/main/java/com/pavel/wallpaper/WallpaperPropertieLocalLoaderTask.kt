package com.wallpaper

import android.content.Context
import android.graphics.BitmapFactory
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.assist.ImageSize
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference



object WallpaperPropertieLocalLoaderTask {

    var wallpaperItem: WallpaperItem? = null
    var wallpaperCallback: WeakReference<CallbackWallpaperLocal>? = null
    var context: WeakReference<Context>? = null

    fun init(context: Context): WallpaperPropertieLocalLoaderTask {
        this.context = WeakReference(context)
        return WallpaperPropertieLocalLoaderTask
    }

    fun wallpaper(wallpaper: WallpaperItem): WallpaperPropertieLocalLoaderTask {
        this.wallpaperItem = wallpaper
        return this
    }

    fun callbackWallpaper(wallpaperCallBack: CallbackWallpaperLocal): WallpaperPropertieLocalLoaderTask {
        wallpaperCallback = WeakReference(wallpaperCallBack)
        return this
    }

    fun prepare(context: Context): WallpaperPropertieLocalLoaderTask {
        return init(context)
    }

    fun start(callback: (Boolean) -> Unit) {
        GlobalScope.launch {
            if (wallpaperItem == null) {
                callback(false)
            }

            if (wallpaperItem?.imageDimension != null && wallpaperItem?.mimeType != null ) {
                callback(false)
            }

            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true

            BitmapFactory.decodeFile(wallpaperItem?.imageLink, options)

            val imageSize = ImageSize(options.outWidth, options.outHeight)

            wallpaperItem?.imageDimension = imageSize
            wallpaperItem?.mimeType = options.outMimeType


            /**
             *  One error here about diskCache
             *
             */

            /*
            val target = ImageLoader.getInstance().diskCache.get(wallpaperItem?.imageLink)
            if (target.exists()) {
                wallpaperItem?.imageSize = target.length().toInt()
            }
             */

            if (wallpaperCallback != null && wallpaperCallback?.get() != null) {
                wallpaperCallback!!.get()?.onPropertiesReceived(wallpaperItem!!)
            }

            callback(true)

        }
    }

    interface CallbackWallpaperLocal {
        fun onPropertiesReceived(wallpaper: WallpaperItem)
    }


}

