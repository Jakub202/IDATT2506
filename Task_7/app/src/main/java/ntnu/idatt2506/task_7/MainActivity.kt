package ntnu.idatt2506.task_7

import MyPreferenceManager
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import ntnu.idatt2506.task_7.managers.DBManager
import ntnu.idatt2506.task_7.managers.FileManager
import ntnu.idatt2506.task_7.model.Movie

class MainActivity : AppCompatActivity() {
    private lateinit var preferenceManager: MyPreferenceManager
    private lateinit var dbManager: DBManager
    private lateinit var fileManager: FileManager
    private lateinit var listView: ListView
    private lateinit var moviesAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preferenceManager = MyPreferenceManager(this)
        dbManager = DBManager(this)
        fileManager = FileManager(this)
        listView = findViewById(R.id.listViewMovies)

        initializeDatabaseAndFiles()

        // Use the background color from preferences if available
        val color = preferenceManager.getBackgroundColor()
        if (color != -1) {
            listView.setBackgroundColor(color)
        }
    }

    private fun initializeDatabaseAndFiles() {
        // Parse movies from JSON resource and populate the database
        val movies = fileManager.parseMoviesFromJson(R.raw.movies)
        dbManager.clearMoviesTable()
        movies.forEach(dbManager::addMovie)

        // Write the parsed movies to a local file
        fileManager.writeMoviesToFile(movies, "movies.txt")

        // Update the movie list view
        updateMovieList(movies)
    }

    override fun onResume() {
        super.onResume()
        // Update the background color from preferences if available
        val color = preferenceManager.getBackgroundColor()
        listView.setBackgroundColor(color) // Assuming 'listView' is the view you want to change
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            R.id.filter_all -> {
                updateMovieList(dbManager.getAllMovies())
                true
            }
            R.id.filter_by_director_nolan -> {
                showMoviesByDirector("Christopher Nolan")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showMoviesByDirector(director: String) {
        val movies = dbManager.getMoviesByDirector(director)
        updateMovieList(movies)
    }

    private fun updateMovieList(movies: List<Movie>) {
        val movieTitles = movies.map { it.title }
        moviesAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, movieTitles)
        listView.adapter = moviesAdapter
    }

}