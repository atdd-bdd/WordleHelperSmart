package com.kenpugh.wordlehelpersmart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.DeviceFontFamilyName
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

private val textStyle = TextStyle(
    fontSize = 20.sp,
    color = Color.Blue,
    fontFamily = FontFamily.Monospace,
    fontStyle = FontStyle.Italic
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(gameViewModel: GameViewModel = viewModel()) {
    val gameUiState by gameViewModel.uiState.collectAsState()
    val configuration = LocalConfiguration.current
    if (configuration.screenHeightDp < configuration.screenWidthDp) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        )
        {
            Column {
                Text("Guesses", style = textStyle)
                GuessColumn(gameViewModel.guessList, gameViewModel)
                Button(onClick = { gameViewModel.setCurrentGuessWord(Word("ABCDE"))}) {
                    Text("Somethihg else")
                }
            }
            grid(gameViewModel, gameUiState)
            Column {
                Text("Answers", style = textStyle)
                Answercolumn(gameViewModel.answerList)
                Button(onClick = { gameViewModel.setCurrentGuessWord(Word("ABCDE"))}) {
                    Text("Lock in Guess")
                }
            }
        }

    } else {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(horizontalArrangement = Arrangement.Center) {
                grid(gameViewModel, gameUiState)
            }
            Row(horizontalArrangement = Arrangement.Center) {
                Column {
                    Row {
                        Text("Guesses", style = textStyle)
                    }
                    Row {
                        GuessColumn(gameViewModel.guessList, gameViewModel)
                    }
                    Row {
                        GuessEntryField(gameViewModel)

                    }
                    Row {
                         Button(onClick = { gameViewModel.setCurrentGuessWord(Word("ABCDE"))}) {
                        Text("Somethihg else")
                         }

                    }

                }
                Column {
                    Text("Answers", style = textStyle)
                    Answercolumn(gameViewModel.answerList)
                    Button(onClick = { gameViewModel.incrementGuessIndex()}) {
                        if (gameViewModel.game_over)
                            Text("Reset Game")
                        else
                            Text("Lock in Guess");

                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun GuessEntryField(gameViewModel: GameViewModel) {
    OutlinedTextField(
        value = gameViewModel.current_guess_word.toString(), singleLine = true,
        onValueChange = { gameViewModel.setCurrentGuessWord(Word(it)) },
        label = { Text("Guess") },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        modifier = Modifier.size(width = 100.dp, height = 60.dp)
    )
}

private val listModifier = Modifier
    .background(Color.Gray)
    .padding(10.dp)

@Composable
private fun Answercolumn(answers: List<Word>) {
    LazyColumn(
        modifier = Modifier
            .background(Color.Gray)
            .padding(10.dp).fillMaxHeight(.7f),
        contentPadding = PaddingValues(
            start = 20.dp,
            top = 5.dp,
            end = 20.dp,
            bottom = 10.dp
        )
    ) {
        // Add a single item
        for (answer in answers) {
            item { Text(text = answer.toString(), fontFamily = FontFamily.Monospace) }
        }
    }
}

@Composable
private fun GuessColumn(guesses: List<Word>, gameViewModel: GameViewModel) {
    LazyColumn(
        modifier = Modifier
            .background(Color.Gray)
            .padding(10.dp).fillMaxHeight(.6f),
        contentPadding = PaddingValues(
            start = 20.dp,
            top = 5.dp,
            end = 20.dp,
            bottom = 10.dp
        ),
    ) {
        // Add a single item
        var index = 0;
        for (answer in guesses) {
            item {
                val current = answer
                ClickableText(text = AnnotatedString(answer.toString()),    style = TextStyle(
                    color = Color.Blue,
                    fontSize = 26.sp,
                    fontFamily = FontFamily.Monospace),
                    onClick = {gameViewModel.setCurrentGuessWord(current) })
            }
            index++
        }
    }
}


@Composable
private fun grid(
    gameViewModel: GameViewModel, gameState: GameModel,

    ) {


    LazyVerticalGrid(
        // content padding
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(Color.Yellow)
            .wrapContentWidth(unbounded = true)
            .widthIn(min = 0.dp, max = 400.dp),

        columns = GridCells.Fixed(5),
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        )
    ) {
        val numbers = (0..29).toList()
        items(numbers.size) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CharButton(it, gameViewModel, gameState)
            }
        }
    }
}

private val buttonTextStyle =
    TextStyle(fontSize = 20.sp, color = Color.White, fontFamily = FontFamily.Monospace)


@Composable
private fun CharButton(
    it: Int, gameViewModel: GameViewModel,
    gameModel: GameModel,
) {
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
        contentPadding = PaddingValues(
            start = 1.dp,
            top = 1.dp,
            end = 1.dp,
            bottom = 1.dp
        ),
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
        //modifier = Modifier.fillMaxSize(0.3F)

    ) {
        Text(text = gameViewModel.enterGuessList[it], style = buttonTextStyle)

    }
}

