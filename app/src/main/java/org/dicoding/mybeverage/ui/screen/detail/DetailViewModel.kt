package org.dicoding.mybeverage.ui.screen.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.dicoding.mybeverage.data.model.Beverage
import org.dicoding.mybeverage.data.repository.BeverageRepository
import org.dicoding.mybeverage.ui.common.UiState

class DetailViewModel(private val repository: BeverageRepository):ViewModel(){

    private val _uiState: MutableStateFlow<UiState<Beverage>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Beverage>>
        get() = _uiState

    fun getBeverageById(id: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getBeverageById(id))
        }
    }
}
