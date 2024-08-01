package org.dicoding.mybeverage.di

import org.dicoding.mybeverage.data.repository.BeverageRepository
import org.dicoding.mybeverage.ui.screen.detail.DetailViewModel
import org.dicoding.mybeverage.ui.screen.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    single { BeverageRepository() }
    viewModel { HomeViewModel(get()) }
}

val detailModule = module {
    single { BeverageRepository() }
    viewModel { DetailViewModel(get()) }
}