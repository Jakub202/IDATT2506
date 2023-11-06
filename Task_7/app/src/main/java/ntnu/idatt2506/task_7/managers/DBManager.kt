package ntnu.idatt2506.task_7.managers

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ntnu.idatt2506.task_7.model.Movie

class DBManager(context: Context) : SQLiteOpenHelper(context, "MovieDB", null, 1) {
    companion object {
        private const val TABLE_NAME = "movies"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_DIRECTOR = "director"
        private const val COLUMN_ACTORS = "actors"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
    CREATE TABLE $TABLE_NAME (
        $COLUMN_TITLE TEXT,
        $COLUMN_DIRECTOR TEXT,
        $COLUMN_ACTORS TEXT,
        UNIQUE($COLUMN_TITLE, $COLUMN_DIRECTOR) ON CONFLICT IGNORE
    )
    """.trimIndent()
        db.execSQL(createTable)
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addMovie(movie: Movie) {
        val db = writableDatabase

        // Check if the movie already exists
        val selection = "$COLUMN_TITLE = ? AND $COLUMN_DIRECTOR = ?"
        val selectionArgs = arrayOf(movie.title, movie.director)
        val cursor = db.query(TABLE_NAME, arrayOf(COLUMN_TITLE), selection, selectionArgs, null, null, null)

        // If the movie does not exist, insert it
        if (!cursor.moveToFirst()) {
            val values = ContentValues()
            values.put(COLUMN_TITLE, movie.title)
            values.put(COLUMN_DIRECTOR, movie.director)
            values.put(COLUMN_ACTORS, movie.actors.joinToString(","))
            db.insert(TABLE_NAME, null, values)
        }
        cursor.close()
        db.close()
    }

    @SuppressLint("Range")
    fun getAllMovies(): List<Movie> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val movies = mutableListOf<Movie>()

        if (cursor.moveToFirst()) {
            do {
                val title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
                val director = cursor.getString(cursor.getColumnIndex(COLUMN_DIRECTOR))
                val actors = cursor.getString(cursor.getColumnIndex(COLUMN_ACTORS)).split(",")
                if(!movies.contains(Movie(title, director, actors))) {
                    movies.add(Movie(title, director, actors))
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return movies
    }

    @SuppressLint("Range")
    fun getMoviesByDirector(directorName: String): List<Movie> {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(COLUMN_TITLE, COLUMN_DIRECTOR, COLUMN_ACTORS),
            "$COLUMN_DIRECTOR LIKE ?",
            arrayOf(directorName),
            null,
            null,
            null
        )
        val movies = mutableListOf<Movie>()

        if (cursor.moveToFirst()) {
            do {
                val title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
                val director = cursor.getString(cursor.getColumnIndex(COLUMN_DIRECTOR))
                val actors = cursor.getString(cursor.getColumnIndex(COLUMN_ACTORS)).split(",")
                if(!movies.contains(Movie(title, director, actors))){
                    movies.add(Movie(title, director, actors))
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return movies
    }
    fun clearMoviesTable() {
        val db = writableDatabase
        db.delete(TABLE_NAME, null, null)
        db.close()
    }
}
