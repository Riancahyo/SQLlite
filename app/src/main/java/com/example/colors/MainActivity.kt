package com.example.colors

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.colors.ui.theme.ColorsTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.util.Log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val merah = Color("FF0000","Red")
        val biru = Color("0000FF","Blue")

        lifecycleScope.launch(Dispatchers.IO){
            val db = ColorDatabase.getInstances(this@MainActivity)
            db.colorDao().insert(merah)

            val listOfColor = db.colorDao().getAll()
            for (color in listOfColor){
                Log.d(color.hex, color.name)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ColorsTheme {
        Greeting("Android")
    }
}

