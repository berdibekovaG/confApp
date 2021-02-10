package kz.kolesateam.confapp.di

import kz.kolesateam.confapp.event_details.data.DefaultEventDetailsRepository
import kz.kolesateam.confapp.event_details.data.EventDetailsDataSource
import kz.kolesateam.confapp.event_details.data.EventIdDataSource
import kz.kolesateam.confapp.event_details.data.EventIdMemoryDataSource
import kz.kolesateam.confapp.event_details.domain.EventDetailsRepository
import kz.kolesateam.confapp.event_details.presentation.EventDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

val eventNameModule: Module = module {
    viewModel{
        EventDetailsViewModel(
                eventDetailsRepository = get(),
            favoritesRepository = get(),
            notificationAlarmHelper = get()
        )
    }
    single {
        EventIdMemoryDataSource() as EventIdDataSource
    }
    single {
        val retrofit: Retrofit = get()

        retrofit.create(EventDetailsDataSource::class.java)
    }

    factory {
        DefaultEventDetailsRepository(
                eventDetailsDataSource = get()
        ) as EventDetailsRepository
    }
}