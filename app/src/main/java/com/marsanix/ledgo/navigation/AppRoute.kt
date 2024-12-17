package com.marsanix.ledgo.navigation

sealed class AppRoute(val route: String) {
    object Home : AppRoute("home")
    object Learning : AppRoute("learning")
    object Quiz : AppRoute("quiz")
    object Profile : AppRoute("profile")
    object ArticleDetail : AppRoute("detail")
}