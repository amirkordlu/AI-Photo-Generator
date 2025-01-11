package com.amk.negareh.ui

import android.os.Bundle
import android.view.View
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
import com.amk.negareh.di.myModules
import com.amk.negareh.ui.features.loadingScreen.LoadingScreen
import com.amk.negareh.ui.features.loginScreen.LoginScreen
import com.amk.negareh.ui.features.mainScreen.MainScreen
import com.amk.negareh.ui.features.photoGeneratorScreen.PhotoGeneratorScreen
import com.amk.negareh.ui.features.resultScreen.PhotoResultScreen
import com.amk.negareh.ui.features.shopScreen.ShopScreen
import com.amk.negareh.ui.theme.PhotoGeneratorTheme
import com.amk.negareh.util.MyScreens
import dev.burnoo.cokoin.Koin
import dev.burnoo.cokoin.navigation.KoinNavHost
import org.koin.android.ext.koin.androidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LTR
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

        composable(MyScreens.ShopScreen.route) {
            ShopScreen()
        }

        composable(MyScreens.PhotoGeneratorScreen.route) {
            PhotoGeneratorScreen()
        }

        composable(MyScreens.LoadingScreen.route) {
            LoadingScreen()
        }

        composable(MyScreens.PhotoResultScreen.route) {
            PhotoResultScreen()
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