package com.example.task_2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.task_2.R

class MainActivity : Activity() {
    val RequestCode = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnRandom = findViewById<Button>(R.id.btnRandom)

        // Sett opp en klikklytter for knappen
        btnRandom.setOnClickListener {
            val intent = Intent(this, RandomActivity::class.java)
            intent.putExtra("upper_limit", 100)  // Send øvre grense til RandomActivity
            startActivityForResult(intent, RequestCode)
        }

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RequestCode && resultCode == Activity.RESULT_OK) {
            val randomValue = data?.getIntExtra("random_value", 0)
            val textView = findViewById<TextView>(R.id.my_text_view)
            textView.text = "Tilfeldig tall: $randomValue"
        }
    }

}
