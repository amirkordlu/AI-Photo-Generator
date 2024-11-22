package com.amk.photogenerator.util

sealed class MyScreens(val route: String) {
    object MainScreen : MyScreens("mainScreen")
    object LoginScreen : MyScreens("loginScreen")
    object OTPScreen : MyScreens("otpScreen")
    object SignUpScreen : MyScreens("signUpScreen")
    object ShopScreen : MyScreens("shopScreen")

}
