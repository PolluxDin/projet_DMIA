package com.ding_mouhamed.dm_projet.user

import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import com.ding_mouhamed.dm_projet.data.API
import com.ding_mouhamed.dm_projet.user.ui.theme.DM_projetTheme
import kotlinx.coroutines.launch

import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class UserActivity : ComponentActivity() {
    private val captureUri by lazy {
        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ContentValues())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DM_projetTheme {

                var bitmap: Bitmap? by remember { mutableStateOf(null) }
                var uri: Uri? by remember { mutableStateOf(null) }

                val takePicture = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
                    if (success) uri = captureUri
                    val multipartBody = uri?.toRequestBody()
                    lifecycleScope.launch{
                        multipartBody?.let { it1 -> API.userWebService.updateAvatar(it1).body() }!!
                    }
                }

                val pickPicture = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
                    uri = it
                    val multipartBody = uri?.toRequestBody()
                    lifecycleScope.launch {
                        multipartBody?.let { it1 -> API.userWebService.updateAvatar(it1).body() }!!
                    }
                }

                val pickPhotoPermission = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
                    pickPicture.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }


                Column {
                    AsyncImage(
                        modifier = Modifier.fillMaxHeight(.2f),
                        model = bitmap ?: uri,
                        contentDescription = null
                    )
                    Button(
                        onClick = {takePicture.launch(captureUri)},
                        content = { Text("Take picture") }
                    )
                    Button(
                        onClick = { pickPhotoPermission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE) },
                        content = { Text("Pick photo") }
                    )
                }
            }
        }
    }

    private fun Bitmap.toRequestBody(): MultipartBody.Part {
        val tmpFile = File.createTempFile("avatar", "jpg")
        tmpFile.outputStream().use { // *use* se charge de faire open et close
            this.compress(Bitmap.CompressFormat.JPEG, 100, it) // *this* est le bitmap ici
        }
        return MultipartBody.Part.createFormData(
            name = "avatar",
            filename = "avatar.jpg",
            body = tmpFile.readBytes().toRequestBody()
        )
    }

    private fun Uri.toRequestBody(): MultipartBody.Part {
        val fileInputStream = contentResolver.openInputStream(this)!!
        val fileBody = fileInputStream.readBytes().toRequestBody()
        return MultipartBody.Part.createFormData(
            name = "avatar",
            filename = "avatar.jpg",
            body = fileBody
        )
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    DM_projetTheme {
        Greeting("Android")
    }
}

