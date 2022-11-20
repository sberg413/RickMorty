package com.sberg413.rickandmorty.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan("com.sberg413.rickandmorty")
class RepositoryModule

//val repositoryModule = module {
//
//
//    singleOf(::CharacterRepositoryImpl) bind CharacterRepository::class
//
//    viewModelOf( ::MainViewModel)
//    viewModelOf( ::DetailViewModel )
//
//}