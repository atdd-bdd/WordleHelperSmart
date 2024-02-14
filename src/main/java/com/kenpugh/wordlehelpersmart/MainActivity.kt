package com.kenpugh.wordlehelpersmart

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.kenpugh.wordlehelpersmart.ui.theme.WordleHelperSmartTheme


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WordleHelperSmartTheme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )
                {
                    val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
                    GameScreen(sharedPreferences = sharedPref)
                 }
            }
        }

    }
}

