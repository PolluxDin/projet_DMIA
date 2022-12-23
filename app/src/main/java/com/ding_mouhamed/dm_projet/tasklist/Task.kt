package com.ding_mouhamed.dm_projet.tasklist

import android.accounts.AuthenticatorDescription

data class Task(val id: String,
                val title: String,
                val description: String = "default description"
):java.io.Serializable
