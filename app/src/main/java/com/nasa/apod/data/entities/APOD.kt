package com.nasa.apod.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "APOD")
data class APOD(
    @PrimaryKey
    val date: String,
    val explanation: String,
    val title: String,
    val media_type: String,
    val url: String,
    var isFavorite: Boolean = false
)