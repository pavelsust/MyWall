package com.pavel.mywall
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wallpaper.WallpaperApplyTask
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() , WallpaperApplyTask.OnMessageCallBack{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        WallpaperApplyTask.prepare(this)
            .wallpaperLink("https://source.unsplash.com/random/1440x3040/?blue")
            .showProgressMessage(this)
            .to(WallpaperApplyTask.Apply.HOMESCREEN_LOCKSCREEN())
            .start()

    }

    override fun carryBitmap(bitmap: Bitmap) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun carryWallpaperMessage(message: String) {

        runOnUiThread{
            Toast.makeText(applicationContext , ""+message , Toast.LENGTH_SHORT ).show()
        }
    }

}
