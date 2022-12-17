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
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.SwipeScreen.route) {
//        composable(route = Screen.MainScreen.route) {
//            MainScreen(navController = navCotroller)
//        }


        composable(route = Screen.SwipeScreen.route) {
                celebListFilterNames.clear()
                retrieveUserVotes()
                celebListParam.clear()
                retrieveCelebs()
                Screen.SwipeScreen(navController = navController, auth = FirebaseAuth.getInstance())
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