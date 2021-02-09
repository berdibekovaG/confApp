package kz.kolesateam.confapp.di

import kz.kolesateam.confapp.favorite_events.data.DefaultFavoriteEventsRepository
import kz.kolesateam.confapp.favorite_events.domain.FavoriteEventsRepository
import kz.kolesateam.confapp.favorite_events.presentation.FavoritesViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteEventsModule: org.koin.core.module.Module = module {
    viewModel {
        FavoritesViewModel(
                favoritesRepository = get(),
                notificationAlarmHelper = get()
        )
    }
    single {
        DefaultFavoriteEventsRepository(
                context = androidApplication(),
                objectMapper = get()
        ) as FavoriteEventsRepository
    }
}