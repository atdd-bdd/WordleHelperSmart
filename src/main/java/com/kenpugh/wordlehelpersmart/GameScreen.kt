package com.kenpugh.wordlehelpersmart
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import Word
import android.app.Activity
import android.content.ContentProvider
import android.content.Context
import android.content.SharedPreferences
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.viewmodel.compose.viewModel


private val textStyle = TextStyle(
    fontSize = 20.sp,
    color = Color.Blue,
    fontFamily = FontFamily.Monospace,
    fontStyle = FontStyle.Italic
)


var initialScreenShown by mutableStateOf(false)
var passedContext : Context? = null
fun turnOffShowInitialScreen(sharedPreferences: SharedPreferences){
    with (sharedPreferences.edit()) {
        putBoolean("ShowInitialScreen", false)
        apply()
    }

}
@Composable
fun GameScreen(gameViewModel: GameViewModel = viewModel(), sharedPreferences: SharedPreferences, context: Context) {
    passedContext = context
    val gameUiState by gameViewModel.uiState.collectAsState()
    val configuration = LocalConfiguration.current
    val showInitialScreen = sharedPreferences.getBoolean("ShowInitialScreen", true)
    if (configuration.screenHeightDp < configuration.screenWidthDp) {
        if (showInitialScreen&& !initialScreenShown) {
            InitialScreen(gameViewModel, sharedPreferences)
        } else {
            if (gameUiState.initialized)
                VerticalGame(gameViewModel, gameUiState)
            else {
                InitializingScreen()
                val context = LocalContext.current as Activity
                LaunchedEffect(key1 = Unit )
                {
                    gameViewModel.initalize(context)
//                composableScope.launch {
//                    gameViewModel.initalize(context)
                }
            }
        }

    } else {
        if (showInitialScreen&& !initialScreenShown) {
            InitialScreen(gameViewModel, sharedPreferences)
        } else {
            if (gameUiState.initialized)
                HorizonaGame(gameViewModel, gameUiState)
            else {
                InitializingScreen()
                val context = LocalContext.current as Activity
                LaunchedEffect(key1 = Unit )
                {
                    gameViewModel.initalize(context)
//                composableScope.launch {
//                    gameViewModel.initalize(context)
                }
            }
        }
    }
}

@Composable
private fun InitializingScreen() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(horizontalArrangement = Arrangement.Center) {
            Text(
                text = "Initializing Word Data\n\n"
                        + "The first time app is loaded, this may take a minute.\n\n"
                        + "After that, it may take a few seconds\n",
                modifier = Modifier.padding(16.dp)
            )
        }

    }
}

@Composable
private fun InitialScreen(gameViewModel: GameViewModel, sharedPreferences: SharedPreferences) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(horizontalArrangement = Arrangement.Center) {

            Text(
                text =
                "Welcome to WordleHelper\n\n" +
                        "1. Pick a word on the guess list or answer list\n" +
                        "    You can also enter a word in the guess box\n" +
                        "2. Go to the game on the NYTimes and enter the guess\n" +
                        "3. Click on the letters until the colors match the NYTimes game\n" +
                        "4. Then click on Lock in Guess\n",
                modifier = Modifier.padding(16.dp)
            )

        }
        Row(horizontalArrangement = Arrangement.Center) {
            Button(onClick = { initialScreenShown = true; gameViewModel.setInitialScreenShown() })
            {
                Text("Continue")
            }
        }
            Row(horizontalArrangement = Arrangement.Center) {
                Button(onClick = { turnOffShowInitialScreen(sharedPreferences); initialScreenShown = true; gameViewModel.setInitialScreenShown()  })
                {
                    Text("Do Not Show This Screen Again")
                }
            }
    }
}


@Composable
private fun HorizonaGame(
    gameViewModel: GameViewModel,
    gameUiState: GameModel,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(horizontalArrangement = Arrangement.Center) {
            Grid(gameViewModel, gameUiState)
        }
        Row(horizontalArrangement = Arrangement.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row {
                    Text("Guesses", style = textStyle)
                }
                Row(modifier = Modifier.weight(1f)) {
                    GuessColumn(gameViewModel.guessList, gameViewModel)
                }
                Row {
                    GuessEntryField(gameViewModel)

                }
                Row {
                    ButtonLockInGuess(gameViewModel)
                }

            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row {
                    Text("Answers", style = textStyle)
                }
                Row(modifier = Modifier.weight(1f)) {
                    Answercolumn(gameViewModel.answerList, gameViewModel)
                }
                Row {
                    ButtonResetGame(gameViewModel)
                }
            }
        }
    }
}

@Composable
private fun VerticalGame(
    gameViewModel: GameViewModel,
    gameUiState: GameModel,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    )
    {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                Text("Guesses", style = textStyle)
            }
            Row(modifier = Modifier.weight(1f)) {
                GuessColumn(gameViewModel.guessList, gameViewModel)
            }
            Row {
                GuessEntryField(gameViewModel)
            }
            Row {
                ButtonLockInGuess(gameViewModel)
            }
        }
        Grid(gameViewModel, gameUiState)
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                Text("Answers", style = textStyle)
            }
            Row(modifier = Modifier.weight(1f)) {
                Answercolumn(gameViewModel.answerList, gameViewModel)
            }
            Row {
                ButtonResetGame(gameViewModel)
            }
        }
    }
}

@Composable
private fun ButtonResetGame(gameViewModel: GameViewModel) {
    Button(onClick = {
        gameViewModel.resetGame()
    }) {
        Text("Reset Game")

    }
}

@Composable
fun ButtonLockInGuess(gameViewModel: GameViewModel) {
    Button(onClick = {
//        if(!gameViewModel.incrementGuessIndex()) showNotAWord()
        gameViewModel.incrementGuessIndex()
    }) {
        Text("Lock in Guess")

    }
}


fun showNotAWord() {
    // This breaks the app
    MaterialAlertDialogBuilder(
        passedContext
    )
        .setTitle("Not a guess")
        .setMessage("Word not in possible guesses")
        .setCancelable(true)
        .show()
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
        modifier = Modifier.size(width = 110.dp, height = 70.dp)
    )
}


@Composable
private fun Answercolumn(answers: List<Word>, gameViewModel: GameViewModel) {
    LazyColumn(
        modifier = Modifier
            .background(Color(240, 240, 240))
            .padding(10.dp)
            .fillMaxHeight(1f),
        contentPadding = PaddingValues(
            start = 8.dp,
            top = 5.dp,
            end = 10.dp,
            bottom = 10.dp
        )
    ) {
        var index = 0
        for (answer in answers) {
            item {
                val current = answer
                ClickableText(text = AnnotatedString(answer.toString()), style = TextStyle(
                    color = Color.Blue,
                    fontSize = 26.sp,
                    fontFamily = FontFamily.Monospace
                ),
                    onClick = { gameViewModel.setCurrentGuessWord(current) })
            }
            index++
        }
    }
}

@Composable
fun GuessColumn(guesses: List<Word>, gameViewModel: GameViewModel) {
    LazyColumn(
        modifier = Modifier
            .background(Color(240, 240, 240))
            .padding(10.dp)
            .fillMaxHeight(1f),
        contentPadding = PaddingValues(
            start = 8.dp,
            top = 5.dp,
            end = 10.dp,
            bottom = 10.dp
        ),
    ) {
        // Add a single item
        var index = 0
        for (guess in guesses) {
            item {
                val current = guess
                ClickableText(text = AnnotatedString(guess.toString()), style = TextStyle(
                    color = Color.Blue,
                    fontSize = 26.sp,
                    fontFamily = FontFamily.Monospace
                ),
                    onClick = { gameViewModel.setCurrentGuessWord(current) })
            }
            index++
        }
    }
}


@Composable
private fun Grid(
    gameViewModel: GameViewModel, gameState: GameModel,

    ) {


    LazyVerticalGrid(
        // content padding

        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(Color(250, 250, 250))
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
            val text = gameViewModel.enterGuessList[it]
            val inCurrentWord = gameViewModel.indexInCurrentWord(it)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CharButton(it, gameViewModel, gameState, text, inCurrentWord)
            }
        }
    }
}

private val buttonTextStyle =
    TextStyle(fontSize = 20.sp, color = Color.White, fontFamily = FontFamily.Monospace)


@Composable
private fun CharButton(
    it: Int, gameViewModel: GameViewModel,
    gameModel: GameModel, text: String, inCurrentWord: Boolean,
) {
    val textColor = Color(0, 0, 0)
    val colorNo = Color(150, 150, 150)
    val colorYes = Color(200, 200, 100)
    val colorExact = Color(100, 200, 100)
    // val font = Font(familyName = DeviceFontFamilyName("Courier"))
    val charState = gameModel.states.getState(it)
    var currentColor = when (charState) {
        CharState.NO -> colorNo
        CharState.YES -> colorYes
        CharState.EXACT -> colorExact

    }
    @Suppress("LiftReturnOrAssignment")
    if (text == " ")
        if (inCurrentWord)
            currentColor = Color(127, 127, 127)
        else
            currentColor = Color(240, 240, 240)
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

