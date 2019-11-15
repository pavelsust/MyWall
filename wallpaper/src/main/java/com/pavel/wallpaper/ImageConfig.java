package com.pavel.wallpaper;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.danimahardhika.android.helpers.core.FileHelper;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.utils.L;

import java.io.File;


public class ImageConfig {

    public static ImageLoaderConfiguration getImageLoaderConfiguration(@NonNull Context context) {
        L.writeLogs(false);
        L.writeDebugLogs(false);
        return new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .threadPoolSize(4)
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .diskCacheSize(256 * FileHelper.MB)
                .diskCache(new UnlimitedDiskCache(new File(
                        context.getCacheDir().toString() + "/uil-images")))
                .memoryCacheSize(8 * FileHelper.MB)
                .build();
    }

    public static DisplayImageOptions getDefaultImageOptions() {
        DisplayImageOptions.Builder options = new DisplayImageOptions.Builder();
        options.delayBeforeLoading(10)
                .resetViewBeforeLoading(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(700))
                .cacheOnDisk(true)
                .cacheInMemory(false);
        return options.build();
    }

    public static DisplayImageOptions getWallpaperOptions() {
        DisplayImageOptions.Builder options = new DisplayImageOptions.Builder();
        options.delayBeforeLoading(10)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .cacheOnDisk(true)
                .cacheInMemory(false);
        return options.build();
    }

    public static DisplayImageOptions.Builder getRawDefaultImageOptions() {
        DisplayImageOptions.Builder options = new DisplayImageOptions.Builder();
        options.delayBeforeLoading(10)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY);
        return options;
    }

    public static ImageSize getThumbnailSize() {
        return new ImageSize(300, 300);
    }

    public static ImageSize getBigThumbnailSize() {
        return new ImageSize(600, 600);
    }
}

