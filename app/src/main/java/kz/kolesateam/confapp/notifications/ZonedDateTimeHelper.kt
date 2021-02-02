package kz.kolesateam.confapp.notifications

import org.threeten.bp.ZonedDateTime
import java.text.ParseException

fun getDateTimeByFormat(
    datetime: String
): ZonedDateTime = try {
    ZonedDateTime.parse(datetime) ?: ZonedDateTime.now()
}
catch (e: ParseException) {
    ZonedDateTime.now()
}