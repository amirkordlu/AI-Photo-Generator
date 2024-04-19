package com.amk.photogenerator.util

sealed class MyScreens(val route: String) {
    object MainScreen : MyScreens("mainScreen")

}
