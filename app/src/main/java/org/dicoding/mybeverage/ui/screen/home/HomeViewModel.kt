package org.dicoding.mybeverage.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.dicoding.mybeverage.data.model.Beverage
import org.dicoding.mybeverage.data.repository.BeverageRepository
import org.dicoding.mybeverage.ui.common.UiState

class HomeViewModel(private val repository: BeverageRepository): ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Beverage>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Beverage>>>
        get() = _uiState

    fun getAllBeverage(){
        viewModelScope.launch {
            repository.getBeverages()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { beverages ->
                    _uiState.value = UiState.Success(beverages)
                }
        }
    }
}

