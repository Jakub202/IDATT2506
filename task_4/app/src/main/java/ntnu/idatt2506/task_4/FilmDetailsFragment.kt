package ntnu.idatt2506.task_4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class FilmDetailsFragment : Fragment() {

    private lateinit var filmImageView: ImageView
    private lateinit var filmDescriptionView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_film_details, container, false)
        filmImageView = view.findViewById(R.id.filmImage)
        filmDescriptionView = view.findViewById(R.id.filmDescription)
        filmImageView.setImageResource(R.drawable.no_image)
        return view
    }

    fun updateDetails(name: String, description: String) {
        // Update the TextView with the film's description
        filmDescriptionView.text = description

        // Assuming you have image resources, update the ImageView
        val imageResource = when (name) {
            "The Shawshank Redemption" -> R.drawable.shawshank_redemption
            "The Dark Knight" -> R.drawable.dark_knight
            "Fight Club" -> R.drawable.fightclub
            "The Godfather" -> R.drawable.godfather
            "Inception" -> R.drawable.inception
            else -> R.drawable.no_image
        }

        filmImageView.setImageResource(imageResource)
    }
}
