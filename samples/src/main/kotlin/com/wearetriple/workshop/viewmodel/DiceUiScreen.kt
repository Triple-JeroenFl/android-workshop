package com.wearetriple.workshop.viewmodel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DiceUiScreen(viewModel: DiceRollViewModel) {
    val state by viewModel.uiState.collectAsState() // Observe state changes
    DiceUiContent(
        onDiceRoll = { viewModel.rollDice() },
        firstDieValue = state.firstDieValue,
        secondDieValue = state.secondDieValue,
        numberOfRolls = state.numberOfRolls,
    )
}

@Composable
fun DiceUiContent(
    onDiceRoll: () -> Unit,
    firstDieValue: Int?,
    secondDieValue: Int?,
    numberOfRolls: Int
) {
    val totalDieValue = (firstDieValue ?: 0) + (secondDieValue ?: 0) // Elvis `?:` operator to take 0 if value is null
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        firstDieValue?.let { dieValue -> // `?.let {}` to only draw in this scope when it's not null
            Text(text = "First die roll: $dieValue")
        }
        secondDieValue?.let { dieValue -> // `?.let {}` to only draw in this scope when it's not null
            Text(text = "Second die roll: $dieValue")
        }
        Text(text = "Total dice roll: $totalDieValue")
        Text(text = "Number of rolls: $numberOfRolls")
        Button(
            onClick = { onDiceRoll() }
        ) {
            Text("Roll dice")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    DiceUiContent(onDiceRoll = {}, firstDieValue = 1, secondDieValue = 3, numberOfRolls = 1)
}