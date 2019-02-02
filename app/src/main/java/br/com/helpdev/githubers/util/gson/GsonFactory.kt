package br.com.helpdev.githubers.util.gson

import com.google.gson.GsonBuilder
import java.util.*

object GsonFactory {

    fun getGson() = GsonBuilder().apply { registerTypeAdapter(Calendar::class.java,
        CalendarDeserializer()
    ) }.create()
}
