package com.amk.photogenerator.util

sealed class MyScreens(val route: String) {
    object MainScreen : MyScreens("mainScreen")
    object LoginScreen : MyScreens("loginScreen")
    object ShopScreen : MyScreens("shopScreen")
    object PhotoGeneratorScreen : MyScreens("photoGeneratorScreen")
    object LoadingScreen : MyScreens("loadingScreen")
    object PhotoResultScreen : MyScreens("photoResultScreen")

}
