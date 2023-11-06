package ntnu.idatt2506.task_7.model

data class Movie(
    val title: String,
    val director: String,
    val actors: List<String>
){
    override fun toString(): String {
        return "Movie(title='$title', director='$director', actors=$actors)"
    }
}