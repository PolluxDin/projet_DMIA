package com.ding_mouhamed.dm_projet.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*
import kotlin.collections.HashMap

@Serializable
data class UserUpdate(

    @SerialName("uuid")
    val uuid : String = UUID.randomUUID().toString(),
    @SerialName("type")
    val type : String = "user_update",
    @SerialName("args")
    val args : Pair<String,String>
)
