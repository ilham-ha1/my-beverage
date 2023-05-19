package org.dicoding.mybeverage.ui.navigation

sealed class Screen(val route:String){
    object Home: Screen("home")
    object Profile: Screen("profile")
    object Detail: Screen("detail/{id}"){
        fun createRoute(id: Int) = "detail/$id"
    }
    object Splash: Screen("splash")

}