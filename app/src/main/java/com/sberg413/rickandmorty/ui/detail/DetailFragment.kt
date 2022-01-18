package com.sberg413.rickandmorty.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.sberg413.rickandmorty.databinding.DetailFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val detailViewModel: DetailViewModel by viewModels()
    private var binding: DetailFragmentBinding? = null

    // Passing directly into the viewModel with SavedStateHandle
    // val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // detailViewModel.initWithCharacter(args.character)

        binding?.let {
            it.viewmodel = detailViewModel
            it.lifecycleOwner = viewLifecycleOwner
            it.executePendingBindings()
        }

        detailViewModel.characterData.observe(this, {
            (requireActivity() as AppCompatActivity).supportActionBar?.title = ( "${it.name} Details")
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}