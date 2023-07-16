package com.example.pcgames.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pcgames.databinding.FragmentGameListBinding
import com.example.pcgames.domain.model.GameList
import com.example.pcgames.presentation.adapter.GameAdapter
import com.example.pcgames.presentation.view_model.GameViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameListFragment : Fragment() {
    private lateinit var binding: FragmentGameListBinding
    private lateinit var gameAdapter: GameAdapter
    private val viewModel: GameViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeViewModel()
        refreshListener()
    }

    private fun setupView() {
        binding.recyclerView.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            gameAdapter = GameAdapter()
            adapter = gameAdapter
        }
    }

    private fun refreshListener() {
        binding.refreshLayout.setOnRefreshListener {
            observeViewModel()
            binding.refreshLayout.isRefreshing = false
        }
    }

    private fun observeViewModel() {
        viewModel.getGameList()
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.gameList.collectLatest {
                when {
                    it.loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    it.gameList.isNotEmpty() -> {
                        binding.progressBar.visibility = View.GONE
                        gameAdapter.differ.submitList(it.gameList as ArrayList<GameList>)
                    }

                    it.error.isNotEmpty() -> {
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onClear()
    }

}