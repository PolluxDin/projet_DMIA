package com.ding_mouhamed.dm_projet.detail

import android.os.Bundle
import android.widget.Toast
//import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ding_mouhamed.dm_projet.detail.ui.theme.DM_projetTheme
import com.ding_mouhamed.dm_projet.tasklist.Task
import java.util.*

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DM_projetTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background

                ) {
                    Detail(){
                        intent.putExtra("Task", it)
                        setResult(RESULT_OK,intent)
                        finish()
                    }
                }
            }
        }
    }
}

@Composable
fun Detail(onValidate: (Task) -> Unit) {
    var taskTitle by remember { mutableStateOf( "") }
    var taskDescription by remember { mutableStateOf("") }
    Column(Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(text = "Task Detail", style = MaterialTheme.typography.h2)
        OutlinedTextField(value = taskTitle, onValueChange = { taskTitle = it }, label = { Text("Title") })
        OutlinedTextField(value = taskDescription, onValueChange = { taskDescription = it }, label = { Text("Description") })

        Button(onClick = {
            val newTask = Task(id = UUID.randomUUID().toString(), title = taskTitle, description = taskDescription)
            onValidate(newTask)
        }) {
            Text(text = "Validation")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DM_projetTheme {
        Detail(){}
    }
}