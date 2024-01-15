package com.kenpugh.wordlehelpersmart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kenpugh.wordlehelpersmart.ui.theme.WordleHelperSmartTheme



@Composable
 fun GameScreenOld(gameViewModel: GameViewModelOld = viewModel()) {
    val gameUiState by gameViewModel.uiState.collectAsState()
    Row {

            answercolumn()
        //singlecolumn()
        grid(gameViewModel)
     }
}
@Composable
private fun answercolumn() {
    LazyColumn {
        // Add a single item
        item {
            Text(text = "First answer")
        }

        // Add 5 items
        items(5) { index ->
            Text(text = "Answer: $index")
        }

        // Add another single item
        item {
            Text(text = "Answer item")
        }
    }
}
@Composable
private fun GuessColumn() {
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
private fun grid(
    gameViewModel: GameViewModelOld

    ) {


    LazyVerticalGrid(

        columns = GridCells.Fixed(5)
    ) {
        val numbers = (0..29).toList()
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
                CharButton(it, gameViewModel)
            }
        }
    }
}

@Composable
private fun CharButton(it: Int, gameViewModel: GameViewModelOld) {
    val textColor = Color(0, 0, 0)
    val colorNo = Color(200, 0, 0)
    val colorYes = Color(0, 200, 0)
    val colorExact = Color(0, 0, 200)
    val guessIndex = it / 5
    val charIndex = it % 5
    val currentColor = when (gameViewModel.getState(guessIndex, charIndex)) {
        CharState.NO -> colorNo
        CharState.YES -> colorYes
        CharState.EXACT -> colorExact
        else -> colorNo
    }
    Button(
        onClick = {
            gameViewModel.setState(guessIndex, charIndex)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = currentColor,
            contentColor = textColor,
            disabledContainerColor = currentColor,
            disabledContentColor = textColor
        ),
        enabled = true,
        modifier = Modifier.fillMaxSize(0.5F)
    ) {
        Text(text = "  $it")

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