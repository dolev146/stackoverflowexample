package com.example.stackoverflowexample


import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun SwipeScreen() {

    if (celebListParam.isEmpty()) {
        Text(
            text = "bad fire base didnt got the data...",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    } else {
        Text(
            text = "good fire base got the data...",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }

}


