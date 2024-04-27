package com.amk.photogenerator.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amk.photogenerator.di.myModules
import com.amk.photogenerator.ui.features.loginScreen.LoginScreen
import com.amk.photogenerator.ui.features.mainScreen.MainScreen
import com.amk.photogenerator.ui.theme.PhotoGeneratorTheme
import com.amk.photogenerator.util.MyScreens
import dev.burnoo.cokoin.Koin
import dev.burnoo.cokoin.navigation.KoinNavHost
import org.koin.android.ext.koin.androidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Koin(appDeclaration = {
                androidContext(this@MainActivity)
                modules(myModules)
            }) {
                PhotoGeneratorTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        PhotoGeneratorUI()
                    }
                }
            }
        }
    }
}

@Composable
fun PhotoGeneratorUI() {
    val navController = rememberNavController()
    KoinNavHost(
        navController = navController,
        startDestination = MyScreens.MainScreen.route
    ) {

        composable(MyScreens.MainScreen.route) {
            MainScreen()
        }

        composable(MyScreens.LoginScreen.route) {
            LoginScreen()
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PhotoGeneratorTheme {
       PhotoGeneratorUI()
    }
}