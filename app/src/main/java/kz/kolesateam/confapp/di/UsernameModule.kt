package kz.kolesateam.confapp.di


import kz.kolesateam.confapp.events.data.dataSource.UserNameDataSource
import kz.kolesateam.confapp.events.data.dataSource.UserNameMemoryDataSource
import kz.kolesateam.confapp.events.data.dataSource.UserNameSharedPrefDataSource
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

//два ключа чтобы понимать какой возвращается при запросе у коина
 const val SHARED_PREFS_DATA_SOURCE = "shared_prefs_data_source"
 const val MEMORY_DATA_SOURCE = "memory_data_source"

// кладем в коин
// разложим дата соурсы
val usernameModule: Module = module {
    factory(named(SHARED_PREFS_DATA_SOURCE)) {
        UserNameSharedPrefDataSource(
            sharedPreferences = get()
        ) as UserNameDataSource  //кастим
    }

    single (named(MEMORY_DATA_SOURCE)){
        UserNameMemoryDataSource()as UserNameDataSource
    }
}