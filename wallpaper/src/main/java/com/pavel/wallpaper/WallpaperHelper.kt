package com.wallpaper

import android.content.Context
import android.content.res.Configuration
import android.graphics.Point
import android.graphics.RectF
import android.os.Build
import androidx.annotation.Nullable
import com.danimahardhika.android.helpers.core.WindowHelper
import com.nostra13.universalimageloader.core.assist.ImageSize
import java.io.File

object WallpaperHelper {


    fun getFormate(mimeType: String): String{
        if (mimeType == null){
            return "jpg"
        }
        when (mimeType){
            "image/jpeg" -> return "jpg"
            "image/png" -> return "png"
            else -> return "jpg"
            }
        }


    /**
     *  it return's the device display size
     *
     */
    fun getTargetSize(context: Context): ImageSize{
        var point: Point = WindowHelper.getScreenSize(context)
        var targetHeight: Int = point.y
        var targetWidth : Int = point.x

        if (context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            targetHeight = point.x
            targetWidth = point.y
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            val statusBarHeight = WindowHelper.getStatusBarHeight(context)
            val navBarHeight = WindowHelper.getNavigationBarHeight(context)
            targetHeight += statusBarHeight + navBarHeight
        }

        return ImageSize(targetWidth , targetHeight)

    }


    /**
     * ReactF holds the four point of dimension
     *
     */

    @Nullable
    fun getScaledRestF(rectf : RectF? , heightFactor: Float , widthFactor: Float): RectF{
        var scaleedRectF = RectF(rectf)
        scaleedRectF.top *= heightFactor
        scaleedRectF.bottom *= heightFactor
        scaleedRectF.left *= widthFactor
        scaleedRectF.right *= widthFactor
        return scaleedRectF
    }

}