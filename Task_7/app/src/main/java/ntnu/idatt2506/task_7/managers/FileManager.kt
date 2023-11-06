package ntnu.idatt2506.task_7.managers

import android.content.Context
import ntnu.idatt2506.task_7.model.Movie
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class FileManager(private val context: Context) {
    fun readRawResource(resourceId: Int): String {
        val content = StringBuilder()
        val streamReader = BufferedReader(InputStreamReader(context.resources.openRawResource(resourceId)))
        var line: String?

        while (streamReader.readLine().also { line = it } != null) {
            content.append(line)
        }

        streamReader.close()
        return content.toString()
    }


    fun writeMoviesToFile(movies: List<Movie>, filename: String) {
        // Convert list of movies to a string
        val data = movies.joinToString(separator = "\n") { it.toString() }
        // Use a try-with-resources statement to auto-close the writer
        val outputStreamWriter = OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE))
        outputStreamWriter.use { writer ->
            writer.write(data)
        }
    }

    fun parseMoviesFromJson(resourceId: Int): List<Movie> {
        val movies = mutableListOf<Movie>()
        val rawJson = readRawResource(resourceId)
        val jsonArray = JSONArray(rawJson)

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val title = jsonObject.getString("title")
            val director = jsonObject.getString("director")
            val actorsList = jsonObject.getJSONArray("actors")
            val actors = (0 until actorsList.length()).map { actorsList.getString(it) }
            movies.add(Movie(title, director, actors))
        }

        return movies
    }
}
