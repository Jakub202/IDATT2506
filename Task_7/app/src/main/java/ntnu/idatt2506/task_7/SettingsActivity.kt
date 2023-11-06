package ntnu.idatt2506.task_7

import MyPreferenceManager
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class SettingsActivity : AppCompatActivity() {
    private lateinit var preferenceManager: MyPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        preferenceManager = MyPreferenceManager(this)

        // Button for setting the background color to red
        findViewById<Button>(R.id.buttonColorRed).setOnClickListener {
            saveColorPreference(R.color.redColor)
        }

        // Button for setting the background color to blue
        findViewById<Button>(R.id.buttonColorBlue).setOnClickListener {
            saveColorPreference(R.color.blueColor)
        }

        // Button for setting the background color to green
        findViewById<Button>(R.id.buttonColorGreen).setOnClickListener {
            saveColorPreference(R.color.greenColor)
        }
    }

    private fun saveColorPreference(colorResId: Int) {
        val color = ContextCompat.getColor(this, colorResId)
        preferenceManager.setBackgroundColor(color)
        finish() // Close the activity after selection
    }
}
