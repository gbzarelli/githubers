package br.com.helpdev.githubers.data.gson

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

internal class CalendarDeserializer : JsonDeserializer<Calendar> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Calendar? {
        return json?.asString.let {
            try {
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).apply {
                    timeZone = TimeZone.getTimeZone("UTC")
                }.parse(it)
            } catch (parse: ParseException) {
                null
            }
        }?.let { Calendar.getInstance().apply { timeInMillis = it.time } }
    }

}