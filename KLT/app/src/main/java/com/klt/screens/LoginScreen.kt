package com.klt.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.klt.R
import kotlinx.coroutines.launch

val backgroundColor = Color.White

private fun strengthChecker(password: String): StrengthPasswordTypes =
    when {
        REGEX_STRONG_PASSWORD.toRegex().containsMatchIn(password) -> StrengthPasswordTypes.STRONG
        else -> StrengthPasswordTypes.WEAK
    }

enum class StrengthPasswordTypes {
    STRONG,
    WEAK
}

private const val REGEX_STRONG_PASSWORD =
    "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~\$^+=<>]).{8,20}\$"


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
) {
    var username: String by remember {
        mutableStateOf("")
    }
    Box(modifier = modifier.then(Modifier.fillMaxSize()), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .background(
                    Color(0xFFE9E9E9)
                )
                .fillMaxWidth()
        ) {
            Spacer(Modifier.padding(vertical = 8.dp))
            Text(text = "Username", modifier = Modifier.padding(horizontal = 160.dp))
            TextField(
                username,
                label = { Text("Username") },
                onValueChange = { if (it != " ") username = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            Spacer(Modifier.padding(vertical = 8.dp))

            Text(text = "Password", modifier = Modifier.padding(horizontal = 160.dp))

            var pw: String by remember {
                mutableStateOf("")
            }

            PasswordTextField(
                text = pw,
                validateStrengthPassword = true,
                hasError = false,
                onTextChanged = { newVal: String -> pw = newVal }
            )

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Text(text = "Login")
            }
            var state by remember {
                mutableStateOf(false)
            }

            val text = "Forgot Password state: $state"
            ClickableText(
                text = AnnotatedString(text),
                onClick = { state = !state },
                modifier = Modifier
                    .padding(vertical = 18.dp)
                    .align(Alignment.CenterHorizontally)
            )

        }
    }


}

/*  code inspired by Juan Guillermo Gómez Torres
 Source: https://medium.com/google-developer-experts/how-to-create-a-composable-password-with-jetpack-compose-f1be2d48d9f0
 */
@Composable
fun PasswordTextField(
    text: String,
    semanticContentDescription: String = "",
    validateStrengthPassword: Boolean = false,
    hasError: Boolean = false,
    onHasStrongPassword: (isStrong: Boolean) -> Unit = {},
    onTextChanged: (text: String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val showPassword = remember { mutableStateOf(false) }
    var strengthColor by remember {
        mutableStateOf("D10000")
    }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .semantics { contentDescription = semanticContentDescription }
            .padding(horizontal = 12.dp),
        value = text,
        label = { Text("Password") },
        onValueChange = onTextChanged,
        keyboardOptions = KeyboardOptions.Default.copy(
            autoCorrect = true,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        singleLine = true,
        isError = hasError,
        visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
    )
    Spacer(modifier = Modifier.height(8.dp))
    if (validateStrengthPassword && text != "") {
        val strengthPasswordType = strengthChecker(text)
        if (strengthPasswordType == StrengthPasswordTypes.STRONG) {
            onHasStrongPassword(true)
        } else {
            onHasStrongPassword(false)
        }

        val isStrong by remember {
            mutableStateOf(StrengthPasswordTypes.STRONG)
        }

        var color = 0xFF0000


        LaunchedEffect(isStrong) {
            launch {
                color = if (isStrong == StrengthPasswordTypes.STRONG) 0xFD10000 else 0xFA30000
            }
        }
        Text(
            modifier = Modifier
                .padding(start = 14.dp)
                .semantics {
                    contentDescription = "StrengthPasswordMessage"
                },
            text = buildAnnotatedString {
                withStyle(

                    style = SpanStyle(
                        color = Color(0xFF515151),
                        fontSize = 10.sp,
                    )
                ) {
                    append(stringResource(id = R.string.warning_password_level))
                    withStyle(style = SpanStyle( color = Color(color))) {
                        when (strengthPasswordType) {
                            StrengthPasswordTypes.STRONG -> {
                                color = 0xFD10000
                                append(" ${stringResource(id = R.string.warning_password_level_strong)}")
                            }

                            StrengthPasswordTypes.WEAK ->{
                                color = 0xFA30000
                                append(" ${stringResource(id = R.string.warning_password_level_weak)}")
                            }

                        }
                    }
                }
            }
        )
    }


}
