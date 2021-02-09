package kz.kolesateam.confapp.events.data.network

import kz.kolesateam.confapp.events.data.dataSource.UpcomingEventDataSource
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

private const val BASE_URL = " http://37.143.8.68:2020"

val apiRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(JacksonConverterFactory.create())
        .build()

val apiClient: UpcomingEventDataSource = apiRetrofit.create(UpcomingEventDataSource::class.java)