package com.klt.ui.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.navigation.NavController
import com.klt.screens.Client
import com.klt.screens.KLTItem
import com.klt.screens.Task

@Composable
fun LazyWindow(
    modifier: Modifier = Modifier,
    navController: NavController,
    destination: String,
    items: List<KLTItem>,
    repeats: Int = 1
) {
    LazyColumn(
        modifier = Modifier
            .height(
                max(
                    200.dp,
                    LocalConfiguration.current.screenHeightDp.dp / 3
                )
            )
    ) {
        items(items = items, key = { item -> item.name }) { item ->
            repeat(repeats) {
                val bgColor: Color = when (item) {
                    is Client -> Color.LightGray
                    is Task -> Color(0xFFE30613) // KLT Red
                    else -> Color.Transparent
                }
                val textColor: Color = when (item) {
                    is Task -> Color.White
                    else -> Color.Black
                }
                EntryCard(
                    item = item,
                    textColor = textColor,
                    navController = navController,
                    destination = destination,
                    hasIcon = item.hasIcon,
                    backgroundColor = bgColor
                )
                Spacer(modifier = Modifier.height(7.dp))
            }
        }
    }
}