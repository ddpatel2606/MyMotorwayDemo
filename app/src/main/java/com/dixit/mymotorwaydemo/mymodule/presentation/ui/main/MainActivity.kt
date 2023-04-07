package com.dixit.mymotorwaydemo.mymodule.presentation.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.dixit.mymotorwaydemo.ui.theme.MyMotorwayDemoTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 *  MainActivity
 *  This is MainActivity screen, It will use MainView as a compose view and calculate the words from given text which will entered in the TextField.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyMotorwayDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainView(mainActivityViewModel)
                }
            }
        }
    }
}
