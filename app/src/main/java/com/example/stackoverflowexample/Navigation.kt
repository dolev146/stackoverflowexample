package com.example.stackoverflowexample

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation() {
    val navCotroller = rememberNavController()
    NavHost(navController = navCotroller, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navCotroller)
        }
        // if we want the name to be optional we can use navArgument with nullable = true
        // we specify the route with ? to make it optional and we can use it in the composable
        // ?name={name}
        // if we want more than 1 parameter
        // /{name}/{age}
        composable(route = Screen.DetailScreen.route + "/{name}" , arguments = listOf(
            navArgument("name") {
                type = NavType.StringType
                defaultValue = "Yaakov"
                nullable = true
            }
        )
        ){entry ->
            DetailScreen(name = entry.arguments?.getString("name"))
        }
    }
}


@Composable
fun MainScreen(navController: NavController) {
    var text by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 50.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(value = text, onValueChange = { text = it }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        Button(modifier = Modifier.align(Alignment.End), onClick = {
            navController.navigate(Screen.DetailScreen.withArgs(text))
        }) {
            Text("To Detail Screen")
        }
    }
}


@Composable
fun DetailScreen(name: String?) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Hello $name")
    }

}