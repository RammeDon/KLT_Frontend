package com.klt.ui.navigation

/**
 *
 *  Adapted from source: android.developers.com
 */
/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Creates a routing interface through which different views may be accessed as states in the
 * navController's backstack
 */
sealed interface NavPath {
    val icon: ImageVector?
    val route: String
}

object Login : NavPath {
    override val route = "faqs"
    override val icon: ImageVector = Icons.Rounded.Login
}

object Home : NavPath {
    override val route = "home"
    override val icon: ImageVector = Icons.Rounded.Home
}

object Clients : NavPath {
    override val route = "analyzer"
    override val icon: ImageVector = Icons.Rounded.PeopleAlt
}

object Tasks : NavPath {
    override val route = "analyzer"
    override val icon: ImageVector = Icons.Rounded.TaskAlt
}

object Settings : NavPath {
    override val icon = Icons.Rounded.Settings
    override val route = "settings"
}

object CreateUser : NavPath {
    override val icon = Icons.Rounded.Settings
    override val route = "create-user"
}

object Admin : NavPath {
    override val icon = Icons.Rounded.Settings
    override val route = "admin"
}

object User : NavPath {
    override val icon = Icons.Rounded.Settings
    override val route = "user"
}

object ResetPassword : NavPath {
    override val icon = Icons.Rounded.LockReset
    override val route = "reset-password"
}

object ForgotPassword : NavPath {
    override val icon = Icons.Rounded.QuestionMark
    override val route = "forgot-password"
}