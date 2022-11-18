package com.sberg413.rickandmorty.models


@JvmInline
value class SearchFilter(val search: String?)
val NoSearchFilter = SearchFilter(null)

@JvmInline
value class StatusFilter(val status: String?)
val NoStatusFilter = StatusFilter(null)