package com.pavel.mywall
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import com.wallpaper.WallpaperApplyTask



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        WallpaperApplyTask.prepare(this)
            //.wallpaper(wallpaperItem)
            .wallpaperLink("https://wallpaperplay.com/walls/full/0/2/e/105979.jpg")
            .to(WallpaperApplyTask.Apply.HOMESCREEN())
            .start()

    }

}
