package com.wallpaper

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.assist.ImageSize
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.lang.ref.WeakReference as WeakReference1

object WallpaperPropertiesLoaderTask {

    var wallpaperItem: WallpaperItem? = null
    var wallpaperCallback: WeakReference1<CallbackWallpaper>? = null
    var context: WeakReference1<Context>? = null

    fun init(context: Context): WallpaperPropertiesLoaderTask {
        this.context = WeakReference1(context)
        return WallpaperPropertiesLoaderTask
    }

    fun wallpaper(wallpaper: WallpaperItem): WallpaperPropertiesLoaderTask {
        this.wallpaperItem = wallpaper
        return this
    }

    fun callbackWallpaper(wallpaperCallBack: CallbackWallpaper): WallpaperPropertiesLoaderTask {
        wallpaperCallback = WeakReference1(wallpaperCallBack)
        return this
    }

    fun prepare(context: Context): WallpaperPropertiesLoaderTask {
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

            val url = URL(wallpaperItem?.imageLink)

            val httpConnection = url.openConnection() as HttpURLConnection
            httpConnection.connectTimeout = 1500

            if (httpConnection.responseCode == HttpURLConnection.HTTP_OK) {
                val stream: InputStream = httpConnection.inputStream

                BitmapFactory.decodeStream(stream, null, options)

                val imageSize = ImageSize(options.outWidth, options.outHeight)

                wallpaperItem?.imageDimension = imageSize
                wallpaperItem?.mimeType = options.outMimeType

                Log.d("Message" , "from option"+options.inSampleSize)
                Log.d("Message" , "from input"+httpConnection.contentLength)


                /*
                var contentLength = httpConnection.contentLength
                if (contentLength > 0) {
                    wallpaperItem?.imageSize = contentLength
                }

                 */


                stream.close()

                /*
                if (wallpaperItem!!.imageSize!! <= 0) {
                    val target = ImageLoader.getInstance().diskCache.get(wallpaperItem?.imageLink)
                    if (target.exists()) {
                        wallpaperItem?.imageSize = target.length().toInt()
                    }
                }

                 */

                if (wallpaperCallback != null && wallpaperCallback?.get() != null) {
                    wallpaperCallback!!.get()?.onPropertiesReceived(wallpaperItem!!)
                }

                callback(true)

            }
        }
    }

    interface CallbackWallpaper {
        fun onPropertiesReceived(wallpaper: WallpaperItem)
    }

}