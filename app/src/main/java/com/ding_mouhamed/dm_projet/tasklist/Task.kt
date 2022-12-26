package com.ding_mouhamed.dm_projet.tasklist

import android.accounts.AuthenticatorDescription
import kotlinx.serialization.*

@Serializable
data class Task(
    @SerialName("id")
    val id: String,
    @SerialName("content")
    val title: String,
    @SerialName("description")
    val description: String = "default description"
):java.io.Serializable
