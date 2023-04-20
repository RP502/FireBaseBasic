package com.example.firebasetest2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //btn Insert data
        btnInsert.setOnClickListener {
            val intent = Intent(this,InsertProductActivity::class.java)
            startActivity(intent)

        }

        btnFetch.setOnClickListener {
            val intent = Intent(this,FecthActivity::class.java)
            startActivity(intent)
        }
    }
}