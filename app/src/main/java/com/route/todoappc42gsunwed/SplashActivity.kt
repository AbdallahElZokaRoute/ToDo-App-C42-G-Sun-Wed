package com.route.todoappc42gsunwed

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.Future

@SuppressLint("CustomSplashScreen")
class  SplashActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        moveToHome()
        }
    fun moveToHome(){
        lifecycleScope.launch {
            delay(2000)
            startActivity(Intent(this@SplashActivity, HomeAcitvity::class.java))
          finish()
        }
    }
    }

