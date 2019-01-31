/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.helpdev.githubers.data

import androidx.room.TypeConverter
import java.util.Calendar

/**
 * Conversor de tipos complexos aceitos pelo Room.
 * Type converters to allow Room to reference complex data types.
 */
class Converters {

    /**
     * Realiza a conversão de um Calendar para um long.
     * - Caso seu objeto contém um calendar ele passa para um long, assim ele pormite a persistência.
     *
     * Performs the conversion of a Calendar to a long
     * - Case your object contains a calendar, it passes to a long. So it allows persistence
     *
     */
    @TypeConverter fun calendarToDatestamp(calendar: Calendar): Long = calendar.timeInMillis

    /**
     * Realiza a conversão de um long para um Calendar.
     * - Caso seu objeto contém um Calendar e no banco está long ele infla em um Calendar
     *
     * Performs the conversion of a long to a Calendar.
     * - Case your object contains a Calendar and in database it's a long, it inflates in a Calendar
     *
     */
    @TypeConverter fun datestampToCalendar(value: Long): Calendar =
            Calendar.getInstance().apply { timeInMillis = value }
}