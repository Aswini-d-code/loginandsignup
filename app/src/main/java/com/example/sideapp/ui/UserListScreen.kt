package com.example.sideapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sideapp.data.remote.models.User

@Composable
fun UserListScreen(
    users: List<User>,
    status: String?,
    onRefresh: () -> Unit,
    onCreate: (String, String) -> Unit,
    onUpdate: (Int, String, String) -> Unit,
    onDelete: (Int) -> Unit,
    onLogout: () -> Unit
) {
    var name by remember { mutableStateOf("morpheus") }
    var job by remember { mutableStateOf("leader") }
    var updateId by remember { mutableStateOf("2") }
    var updateName by remember { mutableStateOf("neo") }
    var updateJob by remember { mutableStateOf("the one") }
    var deleteId by remember { mutableStateOf("2") }

    Column(Modifier.padding(16.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Users", style = MaterialTheme.typography.titleLarge)
            TextButton(onClick = onLogout) { Text("Logout") }
        }

        Spacer(Modifier.height(8.dp))
        Button(onClick = onRefresh) { Text("Refresh users") }

        Spacer(Modifier.height(12.dp))
        Text("Create User")
        OutlinedTextField(name, { name = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(job, { job = it }, label = { Text("Job") }, modifier = Modifier.fillMaxWidth())
        Button(onClick = { onCreate(name, job) }, modifier = Modifier.fillMaxWidth()) { Text("Create") }

        Spacer(Modifier.height(12.dp))
        Text("Update User")
        OutlinedTextField(updateId, { updateId = it }, label = { Text("User ID") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(updateName, { updateName = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(updateJob, { updateJob = it }, label = { Text("Job") }, modifier = Modifier.fillMaxWidth())
        Button(onClick = { updateId.toIntOrNull()?.let { onUpdate(it, updateName, updateJob) } }, modifier = Modifier.fillMaxWidth()) {
            Text("Update")
        }

        Spacer(Modifier.height(12.dp))
        Text("Delete User")
        OutlinedTextField(deleteId, { deleteId = it }, label = { Text("User ID") }, modifier = Modifier.fillMaxWidth())
        Button(onClick = { deleteId.toIntOrNull()?.let { onDelete(it) } }, modifier = Modifier.fillMaxWidth()) { Text("Delete") }

        Spacer(Modifier.height(12.dp))
        status?.let { Text(it, color = MaterialTheme.colorScheme.secondary) }

        Spacer(Modifier.height(12.dp))
        LazyColumn {
            items(users) { u ->
                Text("• ${u.firstName} ${u.lastName}  (${u.email})")
            }
        }
    }
}
