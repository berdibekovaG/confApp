package kz.kolesateam.confapp.di

import kz.kolesateam.confapp.allevents.data.AllEventsDataSource
import kz.kolesateam.confapp.allevents.data.DefaultAllEventsRepository
import kz.kolesateam.confapp.allevents.domain.AllEventsActivityRepository
import kz.kolesateam.confapp.events.data.dataSource.UpcomingEventDataSource
import kz.kolesateam.confapp.events.data.models.DefaultUpcomingEventsRepository
import kz.kolesateam.confapp.events.domain.UpcomingEventsRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

private const val BASE_URL = " http://37.143.8.68:2020"

val eventScreenModule: Module = module {

    viewModel {
        UpcomingEventsViewModel(
            upcomingEventsRepository = get(),
            userNameDataSource = get(named(MEMORY_DATA_SOURCE)),
                favoriteEventsRepository = get(),
        notificationAlarmHelper = get()
        )
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }

    single {
        val retrofit: Retrofit = get()

        retrofit.create(UpcomingEventDataSource::class.java)
    }
    viewModel {
        AllEventsViewModel(
            allEventsActivityRepository = get(),
           // userNameDataSource = get(named(MEMORY_DATA_SOURCE))
        )
    }
    single {
        val retrofit: Retrofit = get()

        retrofit.create(AllEventsDataSource::class.java)
    }

    factory() {
        DefaultUpcomingEventsRepository(
            upcomingEventsDataSource = get()
        ) as UpcomingEventsRepository
    }

    factory {
        DefaultAllEventsRepository(
            upcomingAllEventsDataSource = get()
        ) as AllEventsActivityRepository
    }
}