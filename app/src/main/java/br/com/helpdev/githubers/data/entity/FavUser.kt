package br.com.helpdev.githubers.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

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
data class FavUser(@ColumnInfo(name = "user_id") val id: Int)