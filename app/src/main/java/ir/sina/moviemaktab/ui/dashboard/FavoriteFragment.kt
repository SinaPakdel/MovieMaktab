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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentFavoriteBinding.bind(view)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.list.collect { state ->
                    when (state) {
                        is ResponseState.Loading -> {
                            binding.textDashboard.text = "Loading..."
                        }

                        is ResponseState.Success -> {
                            binding.textDashboard.text = state.data.result.toString()
                        }

                        is ResponseState.Error -> {
                            binding.textDashboard.text = state.message
                        }
                    }
                }
            }
        }
    }
}