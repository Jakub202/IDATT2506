package ntnu.idatt2506.server

import android.app.Activity
import android.os.Bundle
import android.widget.TextView

class ServerActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.textView_messages)
        Server(this, textView).start()
    }
}