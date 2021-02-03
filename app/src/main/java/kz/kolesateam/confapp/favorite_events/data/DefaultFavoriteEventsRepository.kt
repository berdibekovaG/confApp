package kz.kolesateam.confapp.favorite_events.data

import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.MapType
import com.fasterxml.jackson.databind.type.TypeFactory
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.data.models.ResponseData
import kz.kolesateam.confapp.favorite_events.domain.FavoriteEventsRepository
import java.io.FileInputStream
import java.io.FileOutputStream

private const val FAVORITES_FILE_NAME = "favorite_events.json"

class DefaultFavoriteEventsRepository(
    private val context: Context,
    private val objectMapper: ObjectMapper
) : FavoriteEventsRepository {

    private var favoriteEvents: MutableMap<Int, EventApiData> = mutableMapOf()

    init{
        val favoriteEventsFromFile: Map<Int, EventApiData> =   getFavoriteEventsFromFile()
        favoriteEvents = mutableMapOf()
        favoriteEvents?.putAll(favoriteEventsFromFile)
    }

    override fun saveFavoriteEvent(eventApiData: EventApiData) {
        eventApiData.id ?: return //если нулл, то ничего не делает и вызывает return

        favoriteEvents[eventApiData.id]= eventApiData
        saveFavoriteEventsToFile()
    }

    override fun removeFavoriteEvent(eventId: Int?) {
        favoriteEvents.remove(eventId)
        saveFavoriteEventsToFile()
    }

    override fun getAllFavoriteEvents(): List<EventApiData> {
        return favoriteEvents.values.toList()

    }

    override fun isFavorite(id: Int?): Boolean{
       return favoriteEvents.containsKey(id)
    }
    private fun saveFavoriteEventsToFile(){
        val favoriteEventsJsonString: String = objectMapper.writeValueAsString(favoriteEvents)
        val fileOutPutStream: FileOutputStream = context.openFileOutput(
            FAVORITES_FILE_NAME,
            Context.MODE_PRIVATE
        )
        fileOutPutStream.write(favoriteEventsJsonString.toByteArray())
        fileOutPutStream.close()
    }

    private fun getFavoriteEventsFromFile(): Map<Int, EventApiData>{
        var fileInputStream: FileInputStream ?= null
        try{
            fileInputStream= context.openFileInput(FAVORITES_FILE_NAME)
        }catch (exception: Exception){
            fileInputStream?.close()
            return emptyMap()
        }
            val favoriteEventsJsonString = fileInputStream?.bufferedReader()?.readLines()?.joinToString().orEmpty()
        val mapType: MapType = objectMapper.typeFactory.constructMapType(Map:: class.java, Int:: class.java, EventApiData:: class.java)

        return objectMapper.readValue(favoriteEventsJsonString, mapType)
    }
}