package ntnu.idatt2506.task_4

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), FilmListFragment.OnItemSelectedListener {

    private lateinit var filmListFragment: FilmListFragment
    private lateinit var filmDetailsFragment: FilmDetailsFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOrientation(resources.configuration)
        fillFragments()

    }

    private fun fillFragments() {
        filmListFragment = FilmListFragment()
        filmDetailsFragment = FilmDetailsFragment()


        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragmentFilmList, filmListFragment)
            replace(R.id.flFragmentFilmDetails, filmDetailsFragment)
            commit()
        }
    }



    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setOrientation(newConfig)
    }

    private fun setOrientation(newConfig: Configuration) {
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main_vertical)
        } else {
            setContentView(R.layout.activity_main_horizontal)
        }
        fillFragments()
    }


    override fun onItemSelected(name: String, description: String) {
        val detailsFragment = supportFragmentManager.findFragmentById(R.id.flFragmentFilmDetails) as FilmDetailsFragment
        detailsFragment.updateDetails(name, description)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_prev -> {
                previousMovie()
            }
            R.id.action_next -> {
                nextMovie()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun nextMovie(){
        val filmListFragment = supportFragmentManager.findFragmentById(R.id.flFragmentFilmList) as FilmListFragment
        filmListFragment.nextMovie()
    }

    private fun previousMovie(){
        val filmListFragment = supportFragmentManager.findFragmentById(R.id.flFragmentFilmList) as FilmListFragment
        filmListFragment.previousMovie()
    }


}
