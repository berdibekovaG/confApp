package kz.kolesateam.confapp.di

import kz.kolesateam.confapp.events.data.dataSource.UpcomingEventDataSource
import kz.kolesateam.confapp.events.presentation.UpcomingEventsActivity
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

private const val BASE_URL = " http://37.143.8.68:2020"

val eventScreenModule: Module = module {

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

    factory {
        UpcomingEventsActivity(

       //     upcomingEventDataSource = get()
        )
    }

    }