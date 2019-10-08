package com.zakariyaf.wikibrowser.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.zakariyaf.wikibrowser.R

class SplashActivity : AppCompatActivity() {

    var app_name: TextView? = null
    var icon_image: ImageView? = null
    var constraintLayout: ConstraintLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //hiding title bar of this activity
        window.requestFeature(Window.FEATURE_NO_TITLE)
        //making this activity full screen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash)


        constraintLayout = findViewById(R.id.root_background)
        icon_image = findViewById(R.id.imgv_icon)
        app_name = findViewById(R.id.textView2)

        icon_image?.animate()
            ?.scaleX(2.3f)
            ?.scaleY(2.3f)
            ?.duration = 1100

        app_name?.animate()
            ?.scaleX(1.3f)
            ?.scaleY(1.3f)
            ?.duration = 1100


        Handler().postDelayed({
            //start main activity
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            //finish this activity
            finish()
        }, 3000)
    }

}
