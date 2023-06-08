package com.example.mygithubapp3.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import com.example.mygithubapp3.R

//sealed class ScreenExtended(
//    private val baseRoute: String,
//    val arguments: List<NamedNavArgument> = emptyList(),
//) {
//    private val route: String = run {
//        var temp = baseRoute
//        arguments.forEach {
//            val (name) = it
//            temp += "/{$name}"
//            Log.d(javaClass.simpleName, "route: $temp")
//        }
//        temp
//    }
//
//    @Throws(IllegalArgumentException::class, IllegalStateException::class)
//    fun createRoute(vararg args: Any): String {
//        if (arguments.isEmpty())
//            throw IllegalStateException("Class member 'arguments' must not be empty to call this function. If 'arguments' is empty, you should not call this function then")
//
//        if (args.size != arguments.size)
//            throw IllegalArgumentException("The number of arguments passed to this function does not match the number of member class 'arguments'")
//
//        val mappedArgs = arguments.zip(args).toMap()
//        var generatedRoute = baseRoute
//
//        mappedArgs.forEach { (navArg, value) ->
//            generatedRoute += "/$value"
//        }
//
//        Log.d(javaClass.simpleName, "generatedRoute: $generatedRoute")
//        return generatedRoute
//    }
//
//    object Home : ScreenExtended("home")
//    object Cart : ScreenExtended("cart")
//    object Profile : ScreenExtended("profile")
//
//    object DetailReward : ScreenExtended(
//        "home",
//        listOf(navArgument("rewardId") { type = NavType.LongType })
//    )
//}

sealed class Screen(
    var route: String,
) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object About : Screen("about")
    object Detail : Screen("detail/{username}") {
        fun createRoute(username: String): String = "detail/$username"
    }

    companion object {
        val navigationItems = listOf(
            NavigationItem(Home, R.string.nav_home, Icons.Default.Home),
            NavigationItem(Favorite, R.string.nav_favorites, Icons.Default.Favorite),
            NavigationItem(About, R.string.nav_about, Icons.Default.Info)
        )
    }
}
