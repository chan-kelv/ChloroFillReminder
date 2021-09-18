package com.kelvinfocus.chlorofillreminder.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kelvinfocus.chlorofillreminder.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        setContent {
//            MyApp {
//                MyScreenContent()
//            }
//        }
    }
}

//@Composable // handles the basic theming
//fun MyApp(content: @Composable () -> Unit) {
//    ChloroFillReminderTheme {
//        // A surface container using the 'background' color from the theme
//        Surface(color = MaterialTheme.colors.background) {
////            Greeting("Android")
//            content()
//        }
//    }
//}
//
//@Composable
//fun MyScreenContent() {
//    Column() {
//        Greeting(name = "Kelvin")
//        AddNewPlantButton()
//    }
//}
//@Composable
//fun AddNewPlantButton() {
//    Button(onClick = { /*TODO*/ }) {
//        Text("Add new plant")
//    }
//}
//@Composable
//fun Greeting(name: String) {
//    Text(text = "Hello $name!")
//}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    MyApp {
//        MyScreenContent()
//    }
//}