package com.example.sithum.sampleapplication.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar
import com.example.sithum.sampleapplication.R




class LauncherActivity : AppCompatActivity() {


    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        progressBar=findViewById(R.id.progressBar)

       Thread(
           Runnable {
               showProgress()
               startActivity()
               finish()
           }
       ).start()



    }

    private fun showProgress(){
        var progress = 0
        while (progress < 100) {
            try {
                Thread.sleep(200)
                progressBar.setProgress(progress)
            } catch (e: Exception) {
                e.printStackTrace()

            }

            progress += 10
        }
    }

    private fun startActivity(){
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }

}
