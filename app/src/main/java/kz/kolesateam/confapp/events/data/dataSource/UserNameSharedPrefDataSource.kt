package kz.kolesateam.confapp.events.data.dataSource

import android.content.SharedPreferences

private const val USER_NAME_KEY = "user name"
private const val EMPTY_STRING = ""

class UserNameSharedPrefDataSource(
        private val sharedPreferences: SharedPreferences
) : UserNameDataSource {
    override fun getUserName(): String? = sharedPreferences.getString(USER_NAME_KEY, EMPTY_STRING)

    override fun saveUserName(
            userName: String?
    ) {
        sharedPreferences.edit().putString(USER_NAME_KEY, userName).apply()
    }
}