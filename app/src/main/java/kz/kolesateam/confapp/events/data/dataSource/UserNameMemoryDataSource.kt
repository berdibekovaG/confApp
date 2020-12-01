package kz.kolesateam.confapp.events.data.dataSource

// сохранение нейм в поле и реализация
class UserNameMemoryDataSource: UserNameDataSource {

    private var userName: String? = null

    override fun getUserName(): String? = userName

    override fun saveUserName(userName: String?) {
       this.userName = userName
    }
}