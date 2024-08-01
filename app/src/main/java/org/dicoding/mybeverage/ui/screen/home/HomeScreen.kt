package org.dicoding.mybeverage.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import org.dicoding.mybeverage.data.model.Beverage
import org.dicoding.mybeverage.ui.common.UiState
import org.dicoding.mybeverage.ui.component.BeveragesItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val openAlertDialog = remember { mutableStateOf(false) }
    val selectedBeverage = remember { mutableStateOf<Beverage?>(null) }

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllBeverage()
            }
            is UiState.Success -> {
                Scaffold(
                    scaffoldState = scaffoldState,
                    content = { padding ->
                        HomeContent(
                            beverage = uiState.data,
                            modifier = modifier.padding(padding),
                            onItemClick = { beverage ->
                                selectedBeverage.value = beverage
                                openAlertDialog.value = true
                            }
                        )
                    }
                )
            }
            is UiState.Error -> {}
        }
    }

    // Show AlertDialog when openAlertDialog is true
    if (openAlertDialog.value) {
        selectedBeverage.value?.let { beverage ->
            AlertDialog(
                onDismissRequest = { openAlertDialog.value = false },
                title = { Text("Beverage Details") },
                text = { Text("Are you sure you want to view details for ${beverage.name}?") },
                confirmButton = {
                    Button(
                        onClick = {
                            // Close the AlertDialog
                            openAlertDialog.value = false
                            navigateToDetail(beverage.id)
                        }
                    ) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { openAlertDialog.value = false }
                    ) {
                        Text("No")
                    }
                }
            )
        }
    }
}

@Composable
fun HomeContent(
    beverage: List<Beverage>,
    modifier: Modifier = Modifier,
    onItemClick: (Beverage) -> Unit
) {
    LazyColumn(
        modifier = modifier.testTag("BeverageList")
    ) {
        items(beverage) { data ->
            BeveragesItem(
                beverage = data,
                modifier = modifier.clickable {
                    onItemClick(data)
                }
            )
        }
    }
}
