package com.ding_mouhamed.dm_projet.data

import retrofit2.Response
import retrofit2.http.GET

interface UserWebService {
    @GET("/sync/v9/user/")
//    @GET("https://api.todoist.com/sync/v9/user/")
    suspend fun fetchUser(): Response<User>
}