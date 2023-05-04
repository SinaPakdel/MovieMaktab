package ir.sina.moviemaktab.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import ir.sina.moviemaktab.R
import ir.sina.moviemaktab.databinding.FragmentHomeBinding
import ir.sina.moviemaktab.util.ResponseState

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val TAG = "HomeFragment"
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = DataBindingUtil.bind(view)
        binding.homeViewModle = viewModel
        binding.lifecycleOwner=viewLifecycleOwner


        viewModel.movieList.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseState.Error -> {
                    /**
                     * use INVISIBLE instead GONE for better performance
                     */
                    Log.e(TAG, "onViewCreated: ${it.message}")
                }

                is ResponseState.Loading -> {
                    Log.e(TAG, "onViewCreated: loading")
                }

                is ResponseState.Success -> {
                    Log.e(TAG, "onViewCreated: $${it.data}")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}