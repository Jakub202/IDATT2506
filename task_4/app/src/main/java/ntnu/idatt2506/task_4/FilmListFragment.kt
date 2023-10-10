package ntnu.idatt2506.task_4

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment


class FilmListFragment : Fragment() {

    private val filmList = mutableListOf<Film>()
    private var mListener: OnItemSelectedListener? = null
    private var selectedFilmIndex = 0


    interface OnItemSelectedListener {
        fun onItemSelected(name: String, description: String)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = try {
            activity as OnItemSelectedListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                "$activity must implement OnFragmentInteractionListener"
            )
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_film_list, container, false)

        loadFilms()

        val filmNames = filmList.map { it.name }.toTypedArray()
        val listView: ListView = view.findViewById(R.id.filmListView)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, filmNames)
        listView.adapter = adapter
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedFilm = filmList[position]
            mListener?.onItemSelected(selectedFilm.name ?: "", selectedFilm.description ?: "")
            selectedFilmIndex = position
        }
        return view
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    private fun loadFilms() {
        val filmTitles = resources.getStringArray(R.array.film_titles)
        val filmDescriptions = resources.getStringArray(R.array.film_descriptions)

        val imageNames = arrayOf(
            "shawshank_redemption.jpg",
            "dark_knight.jpg",
            "fightclub.jpg",
            "godfather.jpg",
            "inception.jpg"
        )

        for (i in filmTitles.indices) {
            val film = Film()
            film.name = filmTitles[i]
            film.description = filmDescriptions[i]
            film.image = imageNames[i]
            filmList.add(film)
        }
    }

    private fun setMovie(position: Int){
        val selectedFilm = filmList[position]
        mListener?.onItemSelected(selectedFilm.name ?: "", selectedFilm.description ?: "")
    }

    public fun nextMovie(){
        if(selectedFilmIndex < filmList.size - 1){
            selectedFilmIndex++
            setMovie(selectedFilmIndex)
        }else{
            selectedFilmIndex = 0
            setMovie(selectedFilmIndex)
        }
    }

    public fun previousMovie(){
        if(selectedFilmIndex > 0){
            selectedFilmIndex--
            setMovie(selectedFilmIndex)
        }else{
            selectedFilmIndex = filmList.size - 1
            setMovie(selectedFilmIndex)
        }
    }





}
