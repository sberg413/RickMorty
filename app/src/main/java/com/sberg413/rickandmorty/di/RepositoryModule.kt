package com.sberg413.rickandmorty.di

import com.sberg413.rickandmorty.repository.CharacterRepository
import com.sberg413.rickandmorty.repository.CharacterRepositoryImpl
import com.sberg413.rickandmorty.utils.ExcludeFromJacocoGeneratedReport
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@ExcludeFromJacocoGeneratedReport
@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule
{

    @Binds
    abstract fun bindCharacterRepository(
        characterRepositoryImpl: CharacterRepositoryImpl
    ): CharacterRepository

}