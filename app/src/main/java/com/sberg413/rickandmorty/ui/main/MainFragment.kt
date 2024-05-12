package com.sberg413.rickandmorty.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent{
                MaterialTheme {
                    MainCharacterListScreen(viewModel = mainViewModel)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().addMenuProvider( StatusMenuProvider(mainViewModel), viewLifecycleOwner, Lifecycle.State.RESUMED)
        lifecycleScope.launch {
            mainViewModel.characterClicked.collectLatest {
                Log.d(TAG, "characterClicked: $it")
                if (it != null) {
                    val action = MainFragmentDirections.actionShowDetailFragment(it)
                    findNavController().navigate(action)
                    mainViewModel.updateStateWithCharacterClicked(null)
                }
            }
        }
    }


    companion object {
        private const val TAG = "MainFragment"

        @Suppress("UNUSED")
        fun newInstance() = MainFragment()
    }

}