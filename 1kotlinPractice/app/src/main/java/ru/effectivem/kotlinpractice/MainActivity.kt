package ru.effectivem.kotlinpractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.effectivem.kotlinpractice.extensions.findInt
import ru.effectivem.kotlinpractice.ui.theme.KotlinPracticeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinPracticeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {

    val list = remember { listOf("hy", 1L, 5.0f, 6.5, 25) }
    var answer by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = answer?.toString() ?: "Пусто", modifier = Modifier.width(100.dp)
        )

        Text(
            text = "Нажми", modifier = Modifier
                .width(160.dp)
                .padding(10.dp)
                .background(Color.Black)
                .clickable { answer = list.findInt() },
            color = Color.White
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KotlinPracticeTheme {
        Greeting()
    }
}