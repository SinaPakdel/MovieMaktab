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
    private val _moveList = MutableLiveData<ResponseState<List<MovieItem>>>()
    val movieList: LiveData<ResponseState<List<MovieItem>>> = _moveList


    private val _loadingState = MutableLiveData(false)
    val loadingState: LiveData<Boolean> = _loadingState

    init {
        getMovieList(1)
    }

    /**
     * This doesn't work
     */
    fun isLoading(rs: ResponseState<*>?): Boolean {
        return rs is ResponseState.Loading
    }

    private fun getMovieList(page: Int) = viewModelScope.launch {
        _loadingState.postValue(true)
//        _moveList.postValue(ResponseState.Loading)
        val movieList: ResponseState<List<MovieItem>> = repository.getMovieList(page)
        _moveList.postValue(movieList)
        _loadingState.postValue(false)
    }

}

