package com.klt.drawers

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.klt.R
import com.klt.ui.composables.EntryCard
import com.klt.ui.composables.TitledDivider
import com.klt.util.LocalStorage
import com.klt.util.SideBarAdminOptions
import com.klt.util.SideBarUserOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

class SideDrawer(
    private val drawerState: DrawerState,
    private val navController: NavController,
    private val modifier: Modifier = Modifier
) {
    @SuppressLint("ComposableNaming")
    @Composable
    fun drawScaffold() {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.KLT_WhiteGray1)),
            topBar = { drawHead(modifier = modifier.scale(1.75f)) },
            content = {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        drawBody(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it)
                        )
                    }
                }
            },
            bottomBar = { drawFooter() }
        )
    }

    @SuppressLint("ComposableNaming")
    @Composable
    private fun drawHead(modifier: Modifier = Modifier) {
        val coroutine = rememberCoroutineScope()
        val primaryCol = Color.Black
        val primaryAlpha: Float = 1f
        val bgCol = colorResource(id = R.color.KLT_WhiteGray1)

        Column {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .background(colorResource(id = R.color.KLT_Red))
            )
            Row(
                modifier = Modifier
                    .background(bgCol)
                    .padding(bottom = 20.dp)
            ) {
                Spacer(modifier = Modifier.width(12.dp)) // cover scrim area
                /* TODO -- CODE HEAD */
                Row(
                    modifier = Modifier
                        .padding(horizontal = 28.dp)
                        .padding(top = 20.dp)
                        .height(35.dp)
                ) {
                    Text(
                        "Menu", modifier = modifier.then(
                            Modifier
                                .fillMaxHeight()
                                .padding(top = 7.dp)
                                .alpha(primaryAlpha)
                        ),
                        textAlign = TextAlign.Center,
                        color = primaryCol
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = { coroutine.launch { drawerState.close() } },
                        modifier = Modifier.padding(top = 10.dp)
                    ) {
                        Icon(
                            Icons.Rounded.ArrowForward,
                            contentDescription = "Close",
                            tint = primaryCol,
                            modifier = modifier.then(Modifier.alpha(primaryAlpha))
                        )
                    }
                }
            }
        }
    }


    @SuppressLint("ComposableNaming")
    @Composable
    private fun drawBody(modifier: Modifier = Modifier) {
        val coroutine: CoroutineScope = rememberCoroutineScope()
        val dividerCol: Color = colorResource(id = R.color.KLT_DarkGray1)
        val dividerColAlpha = 0.5f
        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .then(modifier)
        ) {
            Spacer(modifier = Modifier.weight(0.25f))
            TitledDivider(
                title = "User",
                dividerCol = dividerCol,
                dividerColAlpha = dividerColAlpha
            )

            SideBarUserOptions.values().forEach {
                EntryCard(
                    item = it.title,
                    textColor = colorResource(id = R.color.KLT_DarkGray1),
                    navController = this@SideDrawer.navController,
                    destination = it.route,
                    hasIcon = it.icon != null,
                    icon = it.icon,
                    isInsideDrawer = true,
                    backgroundColor = colorResource(id = R.color.KLT_WhiteGray1)
                ) { coroutine.launch { drawerState.close() } }
                Spacer(modifier = Modifier.height(5.dp))
            }
            Spacer(modifier = Modifier.weight(1f))


            if (LocalStorage.getIsAdmin(context) == "true") {
                TitledDivider(
                    title = "Admin",
                    dividerCol = dividerCol,
                    dividerColAlpha = dividerColAlpha
                )

                SideBarAdminOptions.values().forEach {
                    EntryCard(
                        item = it.title,
                        textColor = colorResource(id = R.color.KLT_DarkGray1),
                        navController = this@SideDrawer.navController,
                        destination = it.route,
                        hasIcon = it.icon != null,
                        icon = it.icon,
                        isInsideDrawer = true,
                        backgroundColor = colorResource(id = R.color.KLT_WhiteGray1)
                    ) { coroutine.launch { drawerState.close() } }
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }


            Spacer(modifier = Modifier.weight(2f))
        }
    }


    @SuppressLint("ComposableNaming")
    @Composable
    private fun drawFooter(modifier: Modifier = Modifier) {
        val phoneIntent = Intent(Intent.ACTION_DIAL)
        phoneIntent.data = Uri.parse("tel: 0447819000")
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto:info@kjellssons.com")
        val context = LocalContext.current

        Column(
            modifier = Modifier
                .background(colorResource(id = R.color.KLT_DarkGray1))
                .height(
                    max(LocalConfiguration.current.screenHeightDp.dp / 16, 57.dp)
                )
                .then(modifier)
        ) {
            Spacer(Modifier.height(8.dp))
            Text(
                AnnotatedString(stringResource(id = R.string.company_name)), color = colorResource(
                    id = R.color.KLT_WhiteGray1
                ), modifier = Modifier
                    .fillMaxWidth()
                    .alpha(0.5f),
                textAlign = TextAlign.Center, fontSize = 12.sp

            )
            Spacer(Modifier.height(5.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.width(20.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Rounded.Email,
                        contentDescription = "KLT Email",
                        tint = colorResource(id = R.color.KLT_WhiteGray1),
                        modifier = Modifier
                            .scale(0.6f)
                            .alpha(0.5f)
                    )
                    ClickableText(
                        AnnotatedString(stringResource(id = R.string.KLT_email)),
                        style = TextStyle(
                            color = colorResource(
                                id = R.color.KLT_WhiteGray1
                            ),
                            textAlign = TextAlign.Center,
                            fontSize = 9.sp
                        ),
                        modifier = Modifier
                            .alpha(0.5f)
                            .padding(start = 5.dp),
                        onClick = {
                            context.startActivity(emailIntent)
                        }
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        Icons.Rounded.Phone,
                        contentDescription = "KLT Phone",
                        tint = colorResource(id = R.color.KLT_WhiteGray1),
                        modifier = Modifier
                            .scale(0.6f)
                            .alpha(0.5f)
                    )
                    ClickableText(
                        AnnotatedString(stringResource(id = R.string.KLT_phone)),
                        style = TextStyle(
                            color = colorResource(
                                id = R.color.KLT_WhiteGray1
                            ),
                            textAlign = TextAlign.Center,
                            fontSize = 9.sp
                        ),
                        modifier = Modifier
                            .alpha(0.5f)
                            .padding(start = 5.dp),
                        onClick = {
                            context.startActivity(phoneIntent)
                        }
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                }
            }
            Spacer(Modifier.weight(1f))
        }
    }
}
