package br.com.helpdev.githubers.util.gson

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 *  Classe responsável em tratar a deserialização de elementos json para um tipo Calendar.
 *  Caso o objeto esteja mapeado com um atributo do tipo Calendar ele passa por essa rotina para realizar a
 * deserialização;
 *
 *  Essa classe deve ser utilizada no momento de criar o objeto Gson atravez do {@link GsonBuilder()}
 *
 *  Class responsible for handling deserialization of json elements for a Calendar type.
 *  If the object is mapped with a Calendar type attribute it goes through this routine to perform the
 * deserialization;
 *
 *  This class should be used when creating the Gson object through {@link GsonBuilder ()}
 *
 * sample:
 *        GsonBuilder().apply {
 *           registerTypeAdapter(
 *           Calendar::class.java,
 *           CalendarDeserializer()
 *           )
 *       }.create()
 */
internal class CalendarDeserializer : JsonDeserializer<Calendar> {

    companion object {
        private const val DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        private const val DATE_TIME_ZONE = "UTC"
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Calendar? {
        return json?.asString.let {
            try {
                SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).apply {
                    timeZone = TimeZone.getTimeZone(DATE_TIME_ZONE)
                }.parse(it)
            } catch (parse: ParseException) {
                null
            }
        }?.let { Calendar.getInstance().apply { timeInMillis = it.time } }
    }

}