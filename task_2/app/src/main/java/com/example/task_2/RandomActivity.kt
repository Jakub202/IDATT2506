package com.example.task_2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast

class RandomActivity : Activity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val upperLimit = intent.getIntExtra("upper_limit", 100)
        val randomValue = (0..upperLimit).random()
        //Toast.makeText(this, "Tilfeldig tall er: $randomValue", Toast.LENGTH_SHORT).show()
        val resultIntent = Intent()
        resultIntent.putExtra("random_value", randomValue)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

}