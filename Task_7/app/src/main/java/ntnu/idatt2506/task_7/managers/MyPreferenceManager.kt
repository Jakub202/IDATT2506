import android.content.Context
import android.graphics.Color

class MyPreferenceManager(context: Context) {
    private val preferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

    fun setBackgroundColor(color: Int) {
        preferences.edit().putInt("backgroundColor", color).apply()
    }

    fun getBackgroundColor(): Int {
        return preferences.getInt("backgroundColor", Color.WHITE) // Default color
    }
}
