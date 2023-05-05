package ir.sina.moviemaktab.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import ir.sina.moviemaktab.R
import ir.sina.moviemaktab.databinding.FragmentFavoriteBinding
import ir.sina.moviemaktab.util.ResponseState
import ir.sina.moviemaktab.util.viewBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private val binding by viewBinding(FragmentFavoriteBinding::bind)
    private val viewModel: FavoriteViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.list.collect { state ->
                    when (state) {
                        is ResponseState.Loading -> {
                            binding.loadingView.onLoading()
                            delay(10000)
                        }

                        is ResponseState.Success -> {
                            binding.loadingView.onSuccess()
                            binding.textDashboard.text = state.data.result.toString()
                        }

                        is ResponseState.Error -> {
                            binding.loadingView.onFail()
                            binding.textDashboard.text = state.message
                        }
                    }
                }
            }
        }
    }
}

