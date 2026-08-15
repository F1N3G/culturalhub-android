package com.g.culturalhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.g.culturalhub.ui.EventDetailScreen
import com.g.culturalhub.ui.EventListScreen
import com.g.culturalhub.ui.theme.CulturalHubTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CulturalHubTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "events") {

                    composable("events") {
                        EventListScreen(
                            onEventClick = { id -> navController.navigate("event/$id") }
                        )
                    }

                    composable(
                        route = "event/{id}",
                        arguments = listOf(navArgument("id") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val id = backStackEntry.arguments?.getInt("id") ?: 0
                        EventDetailScreen(
                            eventId = id,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}