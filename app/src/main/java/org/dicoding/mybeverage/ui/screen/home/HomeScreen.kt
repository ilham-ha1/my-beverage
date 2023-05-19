package org.dicoding.mybeverage.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.viewmodel.compose.viewModel
import org.dicoding.mybeverage.data.model.Beverage
import org.dicoding.mybeverage.data.repository.BeverageRepository
import org.dicoding.mybeverage.ui.ViewModelFactory
import org.dicoding.mybeverage.ui.common.UiState
import org.dicoding.mybeverage.ui.component.BeveragesItem

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(BeverageRepository())
    ),
    navigateToDetail: (Int) -> Unit,
){
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllBeverage()
            }
            is UiState.Success -> {
                HomeContent(
                    beverage = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    beverage: List<Beverage>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
){
    LazyColumn(
        modifier = modifier.testTag("BeverageList")
    ){
        items(beverage){ data ->
            BeveragesItem(
                beverage = data,
                modifier = modifier.clickable{
                    navigateToDetail(data.id)
                }
            )
        }
    }
}