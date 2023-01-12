package com.ding_mouhamed.dm_projet.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class User(
    @SerialName("id")
    val id: String,
    @SerialName("email")
    val email: String,
    @SerialName("full_name")
    val name: String,
    @SerialName("avatar_medium")
    val avatar: String? = null
)

