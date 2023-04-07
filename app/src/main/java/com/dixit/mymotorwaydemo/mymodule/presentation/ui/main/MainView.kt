package com.dixit.mymotorwaydemo.mymodule.presentation.ui.main

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dixit.mymotorwaydemo.R
import com.dixit.mymotorwaydemo.mymodule.presentation.viewutils.hideKeyboard
import com.dixit.mymotorwaydemo.ui.theme.colorPrimary


/**
 *  MainView
 *  This is Composable MainView, It contains all the Composable Ui elements, which interacts with the ViewModel's Data.
 */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainView(viewModel: MainActivityViewModel) {
    val scaffoldState = rememberScaffoldState()
    val activity = LocalContext.current as Activity

    Scaffold(
        topBar = {
            TopAppBarView()
        },
        floatingActionButton = {
            FloatingActionButton(viewModel)
        },
        floatingActionButtonPosition = FabPosition.Center,
        scaffoldState = scaffoldState
    ) {
        activity.window.statusBarColor = White.toArgb()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp)
        ) {
            MainViewBody(viewModel)
        }
    }
}

/**
 *  MainViewBody
 *  This is MainView's Body
 */
@Composable
fun MainViewBody(viewModel: MainActivityViewModel) {
    val editTextAndCountTaskUiState by viewModel.editTextAndCountTask.collectAsState()
    Column(Modifier.fillMaxSize()) {
        TextField(
            value = editTextAndCountTaskUiState.editText,
            textStyle = MaterialTheme.typography.body2,
            onValueChange = {
                viewModel.updateEditText(it)
                viewModel.calculateWordCount(it)
             },
            modifier = Modifier
                .fillMaxWidth()
                .size(350.dp)
                .padding(16.dp)
                .shadow(0.8.dp, shape = RoundedCornerShape(0.8.dp)),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.edit_text_hint),
                    style = MaterialTheme.typography.body2
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                placeholderColor = Color(0xFFB2B2B2),
                backgroundColor = Color.Transparent
            )
        )
        Text(
            text = stringResource(id = R.string.word_count_string, editTextAndCountTaskUiState.wordCount),
            color = Color.DarkGray,
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 0.dp, end = 16.dp, 0.dp)
        )
    }
}

/**
 *  FloatingActionButton
 */
@Composable
fun FloatingActionButton(viewModel: MainActivityViewModel) {
    val focusManager = LocalFocusManager.current
    FloatingActionButton(
        onClick = {
            focusManager.clearFocus()
            viewModel.getRandomText()
        },
        backgroundColor = MaterialTheme.colors.primaryVariant
    ) {
        Icon(
            imageVector = Icons.Outlined.Refresh,
            contentDescription = stringResource(id = R.string.refresh_button), tint = White
        )
    }
}

/**
 *  TopAppBarView
 */
@Composable
fun TopAppBarView() {
    val context = LocalContext.current
    val activity = LocalContext.current as Activity
    TopAppBar(
        title = { TopAppBarTitleText() },
        modifier = Modifier
            .padding(16.dp)
            .shadow(3.dp, shape = RoundedCornerShape(18.dp)),
        backgroundColor = colorPrimary,
        navigationIcon = {
            IconButton(onClick = {
                hideKeyboard(context, activity)
            }
            ) {
                Icon(Icons.Outlined.Menu, null, tint = Color.Black)
            }
        },
        actions = {
            IconButton(onClick = { }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_car_blue),
                    contentDescription = "",
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(50.dp)
                        .rotate(-135F)
                )
            }
        }
    )
}

/**
 *  TitleText
 */
@Composable
fun TopAppBarTitleText() {
    Text(text = stringResource(id = R.string.app_title), color = Color.Black, style = MaterialTheme.typography.h3)
}