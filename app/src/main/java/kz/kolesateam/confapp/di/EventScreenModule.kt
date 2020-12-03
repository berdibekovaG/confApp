package kz.kolesateam.confapp.di

import kz.kolesateam.confapp.events.data.dataSource.UpcomingEventDataSource
import kz.kolesateam.confapp.events.data.models.DefaultUpcomingEventsRepository
import kz.kolesateam.confapp.events.domain.UpcomingEventsRepository
import kz.kolesateam.confapp.events.presentation.UpcomingEventsActivity
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

private const val BASE_URL = " http://37.143.8.68:2020"
const val DEFAULT_UPCOMING_EVENTS_REPOSITORY = "real_repository"

val eventScreenModule: Module = module {

        viewModel {
            UpcomingEventsViewModel(
                upcomingEventsRepository = get(),
                userNameDataSource = get(named(MEMORY_DATA_SOURCE))
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


    factory() {
        DefaultUpcomingEventsRepository(
            upcomingEventsDataSource = get()
        ) as UpcomingEventsRepository
    }
}