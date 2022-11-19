package com.sberg413.rickandmorty.di

import com.sberg413.rickandmorty.repository.CharacterRepository
import com.sberg413.rickandmorty.repository.CharacterRepositoryImpl
import com.sberg413.rickandmorty.ui.detail.DetailViewModel
import com.sberg413.rickandmorty.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val RepositoryModule = module {


    singleOf(::CharacterRepositoryImpl) bind CharacterRepository::class

    viewModelOf( ::MainViewModel)
    viewModelOf( ::DetailViewModel )

}