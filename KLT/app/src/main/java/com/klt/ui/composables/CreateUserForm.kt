package com.klt.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.klt.util.ApiConnector
import com.klt.util.ApiResult
import com.klt.util.HttpStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

@Composable
fun CreateUserForm(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .then(modifier)
    ) {


        val coroutineScope = rememberCoroutineScope()
        var email by remember { mutableStateOf("") }
        var firstName by remember { mutableStateOf("") }
        var lastName by remember { mutableStateOf("") }
        var alertState by remember { mutableStateOf(FormAlertMsgState.NOT_ACTIVE) }
        var alertMsg by remember { mutableStateOf("") }


        fun updateAlert(msg: String, state: FormAlertMsgState) {
            alertState = state
            alertMsg = msg
        }


        val onCreateAccount: (ApiResult) -> Unit = {

            val data: JSONObject = it.data()
            val msg: String = data.get("msg") as String

            when (it.status()) {
                HttpStatus.SUCCESS -> updateAlert(msg, FormAlertMsgState.GOOD)
                HttpStatus.UNAUTHORIZED -> updateAlert(msg, FormAlertMsgState.BAD)
                HttpStatus.FAILED -> updateAlert(msg, FormAlertMsgState.BAD)
            }
        }


        // Date container
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
            ) {
                FormAlertMsg(msg = alertMsg, state = alertState)
            }

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email..") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
            )

            TextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("First Name..") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
            )

            TextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Last Name..") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
            )

            Button(
                onClick = {
                    coroutineScope.launch(Dispatchers.IO) {
                        ApiConnector.createAccount(
                            "NO TOKEN",     // TODO: Add token
                            email,
                            firstName,
                            lastName,
                            onCreateAccount
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 20.dp,
                        horizontal = 50.dp
                    )
            ) {
                Text(
                    text = "Create Account",
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                )
            }
        }
    }
}