package org.dicoding.mybeverage.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.dicoding.mybeverage.data.model.Beverage
import org.dicoding.mybeverage.data.model.BeveragesData

class BeverageRepository {
    private var beverages = mutableListOf<Beverage>()
    init {
        if(beverages.isEmpty()){
            beverages.addAll(BeveragesData.beverages)
        }
    }

    fun getBeverages(): Flow<List<Beverage>> {
        return flowOf(beverages)
    }

    fun getBeverageById(id: Int): Beverage{
        return beverages.first{
            it.id == id
        }
    }
}