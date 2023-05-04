package ir.sina.moviemaktab.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.sina.moviemaktab.data.repository.MovieRepository
import ir.sina.moviemaktab.model.dto.MovieDto
import ir.sina.moviemaktab.model.dto.MoviesResponseDto
import ir.sina.moviemaktab.util.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {
    private val _list = MutableStateFlow<ResponseState<MoviesResponseDto>>(ResponseState.Loading)
    val list: StateFlow<ResponseState<MoviesResponseDto>> = _list.asStateFlow()

    init {
        setList(1)
    }

    fun setList(page: Int) {
        viewModelScope.launch {
            val listTemp = repository.getMovieListRemote(page)
            listTemp.collect { data ->
                _list.emit(data)
            }
        }
    }
}