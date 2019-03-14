package br.com.helpdev.githubers.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import java.util.*

@Entity(
    tableName = "fav_user",
    primaryKeys = ["user_id"],
    indices = [
        Index("user_id")
    ],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id"]
        )]
)
data class FavUser(
    @ColumnInfo(name = "user_id") val id: Int,
    @ColumnInfo(name = "register_datetime") val registerDateTime: Calendar = GregorianCalendar.getInstance()
)