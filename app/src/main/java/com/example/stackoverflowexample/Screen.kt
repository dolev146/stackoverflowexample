package com.example.stackoverflowexample

sealed class Screen(val route: String) {
    object SwipeScreen : Screen("swipe")
}

