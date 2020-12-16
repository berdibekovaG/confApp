package kz.kolesateam.confapp.events.presentation

import kz.kolesateam.confapp.events.data.models.EventApiData

interface FavoriteClickListener {
    fun onFavoriteClick(eventTitle: EventApiData)
    abstract fun onFavoriteClick(eventTitle: Unit)
}