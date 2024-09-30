package com.himelz.expensetracker

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.himelz.expensetracker.features.addexpense.AddExpenseScreen
import com.himelz.expensetracker.features.home.HomeScreen
import com.himelz.expensetracker.features.statistics.StatisticsScreen

@Composable
fun NavHostScreen() {
    val navController = rememberNavController()
    var bottomBarVisibility by remember { mutableStateOf(true) }


    Scaffold(
        floatingActionButton = {
            AnimatedVisibility(visible = bottomBarVisibility) {
                ExtendedFloatingActionButton(
                    onClick = { navController.navigate(Screens.ADD_EXPENSE_SCREEN.name) }) {
                    Icon(Icons.Default.AddCard, contentDescription = "Add")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Add")
                }
            }
        },
        bottomBar = {
            AnimatedVisibility(visible = bottomBarVisibility) {
                NavigationBottomBar(
                    navController = navController,
                    items = listOf(
                        BottomNavigationItem(
                            route = Screens.HOME_SCREEN.name,
                            selectedIcon = R.drawable.ic_home_filled,
                            unselectedIcon = R.drawable.ic_home
                        ),
                        BottomNavigationItem(
                            route = Screens.STATISTICS_SCREEN.name,
                            selectedIcon = R.drawable.ic_statistic_filled,
                            unselectedIcon = R.drawable.ic_statistic
                        )
                    )
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screens.HOME_SCREEN.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Screens.HOME_SCREEN.name) {
                bottomBarVisibility = true
                HomeScreen(navController)
            }
            composable(route = Screens.STATISTICS_SCREEN.name) {
                bottomBarVisibility = true
                StatisticsScreen(navController)
            }
            composable(route = Screens.ADD_EXPENSE_SCREEN.name) {
                bottomBarVisibility = false
                AddExpenseScreen(navController)
            }
        }
    }
}


data class BottomNavigationItem(
    val route: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
)

@Composable
fun NavigationBottomBar(navController: NavController, items: List<BottomNavigationItem>) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    BottomAppBar {
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = {
                    val iconRes = if (isSelected) item.selectedIcon else item.unselectedIcon
                    Icon(
                        painter = painterResource(id = iconRes),
                        contentDescription = item.route,
                        modifier = Modifier.size(24.dp)
                    )
                },
                alwaysShowLabel = false,
            )
        }
    }
}
