package com.sberg413.rickandmorty.utils

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity


tailrec fun Context.findActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> {
        this
    }
    is ContextWrapper -> {
        baseContext.findActivity()
    }
    else -> {
        null
    }
}