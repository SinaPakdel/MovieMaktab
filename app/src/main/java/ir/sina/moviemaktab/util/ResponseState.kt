package ir.sina.moviemaktab.util

sealed class ResponseState {
    object Response : ResponseState()
    data class Error(val message: String) : ResponseState()
    object Success : ResponseState()
}