package kz.kolesateam.confapp.di


import android.content.Context
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

private const val APPLICATION_SHARED_PREF = "application_shared_prefs"

val applicationModule : Module = module {
    single {
        val context: Context = androidApplication()

        context.getSharedPreferences(APPLICATION_SHARED_PREF, Context.MODE_PRIVATE)
    }
}