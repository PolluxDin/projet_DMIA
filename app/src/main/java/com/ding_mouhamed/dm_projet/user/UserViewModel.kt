package com.ding_mouhamed.dm_projet.user

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ding_mouhamed.dm_projet.data.API
import com.ding_mouhamed.dm_projet.data.User
import com.ding_mouhamed.dm_projet.data.UserUpdate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val webService = API.userWebService

    val userStateFlow = MutableStateFlow(User("","",""))

    fun refresh(){
        viewModelScope.launch {
            val response = webService.fetchUser() // Call HTTP (opération longue)
            if (!response.isSuccessful) { // à cette ligne, on a reçu la réponse de l'API
                Log.e("Network", "Error: ${response.message()}")
                return@launch
            }
            val fetchedUser = response.body()!!
            userStateFlow.value = fetchedUser // on modifie le flow, ce qui déclenche ses observers
        }
    }

    fun edit(user: User) {
        var updatedUser = UserUpdate(user.id,"user_update", Pair(user.name,user.email))
        viewModelScope.launch {
            val response = webService.update(updatedUser) // Call HTTP (opération longue)
            if (!response.isSuccessful) { // à cette ligne, on a reçu la réponse de l'API
                Log.e("Network", "Error: ${response.message()}")
                return@launch
            }
            userStateFlow.value = user
        }
    }

}