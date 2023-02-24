package me.androidbox.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import me.androidbox.component.login.LoginScreen
import me.androidbox.component.login.RegisterScreen
import me.androidbox.presentation.ui.theme.BusbyTaskyTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusbyTaskyTheme {
                RegisterScreen()
            }
        }
    }
}
