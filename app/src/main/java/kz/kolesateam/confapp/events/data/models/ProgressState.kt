package kz.kolesateam.confapp.events.data.models

sealed class ProgressState{
    object Loading: ProgressState()
    object Done: ProgressState()
}