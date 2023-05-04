package ir.sina.moviemaktab.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.sina.moviemaktab.data.repository.MovieRepository
import ir.sina.moviemaktab.model.ui.MovieItem
import ir.sina.moviemaktab.util.ResponseState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _moveList = MutableLiveData<ResponseState<List<MovieItem>>>()
    val movieList: LiveData<ResponseState<List<MovieItem>>> = _moveList

    init {
        getMovieList(1)
    }

    private fun getMovieList(page: Int) = viewModelScope.launch {
        val movieList: ResponseState<List<MovieItem>> = repository.getMovieList(page)
        _moveList.postValue(movieList)
    }

}