package com.kelvinfocus.chlorofillreminder.ui

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.kelvinfocus.chlorofillreminder.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var notificationChannels: List<NotificationChannel>

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            for (channel in notificationChannels) {
                notificationManager.createNotificationChannel(channel)
            }
        }
    }

    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { data ->
                Timber.d("Data: ${data.extras?.getString("image_data")?.length ?: 0}")
            }
        }
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