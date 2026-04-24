package com.valentinerutto.nightlife

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.valentinerutto.nightlife.screen.EventDetailsScreen
import com.valentinerutto.nightlife.screen.EventlistScreen


sealed class Screen(val route: String) {
    data object EventList    : Screen("events")
    data object EventDetail  : Screen("events/{eventId}") {
        fun createRoute(id: String) = "events/$id"
    }}

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.EventList.route) {

        composable(Screen.EventList.route) {
            EventlistScreen(
                onEventClick = { id -> navController.navigate(Screen.EventDetail.createRoute(id)) },
            )
        }

        composable(
            route = Screen.EventDetail.route,
            arguments = listOf(navArgument("eventId") { type = NavType.StringType }),
        ) { back ->
            val eventId = back.arguments!!.getString("eventId")!!
            EventDetailsScreen(
                eventId = eventId,
                onBack = { navController.popBackStack() },
                onBookNow = {  },
            )
        }
    }
}

