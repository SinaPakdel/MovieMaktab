package ir.sina.moviemaktab.model.dto

data class ApiItemError(
    val message: String,
    val isFailed: Boolean,
    val errorCode: Int
)
