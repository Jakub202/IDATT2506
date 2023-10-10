package com.example.task_1

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.task_1.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menu.add("Jakub")
        menu.add("Rysiak")
        Log.d("IDATT2506", "Menu created")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.title == "Jakub") {
            Log.w("IDATT2506", "Fornavn valgt")
        }
        if (item.title == "Rysiak") {
            Log.e("IDATT2506", "Etternavn valgt")
        }
        return true
    }
}
