package com.ding_mouhamed.dm_projet.detail

import android.os.Bundle
//import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    Column(Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(text = "Task Detail", style = MaterialTheme.typography.h2)
        Text(text = "title")
        Text(text = "description")

        Button(onClick = {
            val newTask = Task(id = UUID.randomUUID().toString(), title = "New Task !")
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