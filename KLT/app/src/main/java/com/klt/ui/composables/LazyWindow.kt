package com.klt.ui.composables


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.klt.R
import com.klt.util.ICustomer
import com.klt.util.IKLTItem
import com.klt.util.ITask

@Composable
fun LazyWindow(
    modifier: Modifier = Modifier,
    navController: NavController,
    destination: String,
    items: List<IKLTItem>,
    repeats: Int = 1,
    color: Color,
    icon: ImageVector? = null,
    collapsed: Boolean = false,
) {
    LazyColumn(
        // TODO -- ADD VERTICAL ARRANGEMENT AND TAKE OUT REPEATS
        modifier = Modifier
            .fillMaxHeight()
            .then(modifier),
    ) {
        items(items = items, key = { item -> item.name }) { item ->
            repeat(repeats) {
                Spacer(modifier = Modifier.height(7.dp))
                val bgColor: Color = when (item) {
                    is ICustomer -> Color.LightGray
                    is ITask -> colorResource(id = R.color.KLT_Red) // KLT Red
                    else -> Color.Transparent
                }
                val textColor: Color = when (item) {
                    is ITask -> Color.White
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
            }
        }
    }
}
