package internship.demux.project.model

data class Question(
    val title: String,
    val difficulty: String,
    val companies: List<String>,
    val topics: List<String>,
    val link: String,
    val trending: Boolean,
)