package com.kenpugh.wordlehelpersmart

import com.kenpugh.wordlehelpersmart.State
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap
import com.kenpugh.wordlehelpersmart.ui.theme.WordleHelperSmartTheme
import androidx.lifecycle.viewmodel.compose.viewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gameViewModel: GameViewModel = GameViewModel ()
        // ?? val viewmodel: GameViewModel by viewModels()

        setContent {
            WordleHelperSmartTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )
                                                {
                    singlecolumn()

                    grid(gameViewModel)

                }
            }
        }
    }

    @Composable
    private fun singlecolumn() {
        LazyColumn {
            // Add a single item
            item {
                Text(text = "First item")
            }

            // Add 5 items
            items(5) { index ->
                Text(text = "Item: $index")
            }

            // Add another single item
            item {
                Text(text = "Last item")
            }
        }
    }

    @Composable
    private fun grid  (gameViewModel : GameViewModel
    ) {

        val gameUiState by gameViewModel.uiState.collectAsState()

        LazyVerticalGrid(

            columns = GridCells.Fixed(5)
        ) {
            val numbers = (0..29).toList()
            val model = gameUiState
            items(numbers.size) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    val bitmap = ImageBitmap(30, 30)
//                     Box(
//                        modifier = Modifier
//                            .size(30.dp)
//                            .background(Color(0xFFFBCCBD))
//                    )
//                    Image(bitmap, "Letter")
//                    Text(text = "  $it")
                    val textColor = Color(0,0,0)
                    val colorNo = Color(200, 0, 0)
                    val colorYes = Color(0,200,0)
                    val colorExact = Color(0,0,200)
                    val guessIndex = it / 5
                    val charIndex = it % 5
                    val currentColor = when(model.getState(guessIndex, charIndex)){
                        State.NO -> colorNo
                        State.YES -> colorYes
                        State.EXACT -> colorExact
                        else -> colorNo
                    }
                    Button(
                        onClick = {
                            model.setState(guessIndex, charIndex)
                        },
                        colors = buttonColors(
                            containerColor = currentColor,
                            contentColor = textColor,
                            disabledContainerColor = currentColor,
                            disabledContentColor = textColor
                        ),
                        enabled = true
                    ) {Text(text = "  $it")

                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WordleHelperSmartTheme {
        Greeting("Android")
    }
}