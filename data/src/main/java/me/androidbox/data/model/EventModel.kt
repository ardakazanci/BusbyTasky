package me.androidbox.data.model

data class EventModel(
    val id: String,
    val title: String,
    val description: String,
    val from: Long,
    val to: Long
)
