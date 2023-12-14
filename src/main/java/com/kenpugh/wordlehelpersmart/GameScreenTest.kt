package com.kenpugh.wordlehelpersmart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.font.DeviceFontFamilyName
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GameScreenTest(gameViewModel: GameViewModelTest = viewModel()) {
    val gameUiState by gameViewModel.uiState.collectAsState()
    Row {
        Answercolumn(gameViewModel.gueessList)
        singlecolumn()
        grid(gameViewModel, gameUiState)

    }
}

@Composable
private fun Answercolumn(answers: List<String>) {
    LazyColumn {
        // Add a single item
        for (answer in answers){
            item { Text(text = answer, fontFamily = FontFamily.Monospace)}
        }
    }
}
@Composable
private fun singlecolumn() {
    LazyColumn {

        // Add 5 items
        items(5) { index ->
            Text(text = "Item: $index", fontFamily = FontFamily.Monospace)
        }

        // Add another single item
        item {
            Text(text = "Last item")
        }
    }
}



@Composable
private fun grid(
    gameViewModel: GameViewModelTest, gameState: GameModelTest

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
                CharButton(it, gameViewModel, gameState)
            }
        }
    }
}

@Composable
private fun CharButton(it: Int, gameViewModel: GameViewModelTest,
                       gameModel: GameModelTest) {
    val textColor = Color(0, 0, 0)
    val colorNo = Color(200, 0, 0)
    val colorYes = Color(0, 200, 0)
    val colorExact = Color(0, 0, 200)
    val font = Font(familyName = DeviceFontFamilyName("Courier"))
    val charState = gameModel.states.getState(it)
    val currentColor = when (charState) {
        CharState.NO -> colorNo
        CharState.YES -> colorYes
        CharState.EXACT -> colorExact
        else -> colorNo
    }
    TextButton(
        onClick = {
            val newState = nextCharState(charState)
            gameViewModel.setState(it, newState)
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
        Text(text = "abc")

    }
}

