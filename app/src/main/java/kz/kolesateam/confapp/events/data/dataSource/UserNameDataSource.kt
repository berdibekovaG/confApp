package kz.kolesateam.confapp.events.data.dataSource

interface UserNameDataSource {

    fun getUserName():String?

    fun saveUserName(
        userName: String?
    )
}