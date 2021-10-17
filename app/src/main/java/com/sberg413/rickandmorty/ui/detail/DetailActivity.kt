package com.sberg413.rickandmorty.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sberg413.rickandmorty.R
import com.sberg413.rickandmorty.databinding.DetailActivityBinding

class DetailActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)
        title = "Character Detail"

        val binding: DetailActivityBinding = DataBindingUtil.setContentView(
            this, R.layout.detail_activity)

        intent.getStringExtra("id")?.let { id ->
            val viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

            viewModel.initCharacterId(id)

            binding.viewmodel = viewModel
            binding.lifecycleOwner = this
            binding.executePendingBindings()
        }

    }
}