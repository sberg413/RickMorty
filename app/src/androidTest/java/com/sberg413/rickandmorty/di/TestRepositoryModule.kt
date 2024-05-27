package com.sberg413.rickandmorty.di

import com.sberg413.rickandmorty.repository.CharacterRepository
import com.sberg413.rickandmorty.repository.TestCharacterRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
abstract class TestRepositoryModule
{

    @Binds
    abstract fun bindCharacterRepository(
        characterRepositoryImpl: TestCharacterRepositoryImpl
    ): CharacterRepository

}