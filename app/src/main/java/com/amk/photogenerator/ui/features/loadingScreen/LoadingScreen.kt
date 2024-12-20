package com.amk.photogenerator.ui.features.loadingScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.amk.photogenerator.R
import com.amk.photogenerator.ui.features.photoGeneratorScreen.PhotoGeneratorViewModel
import com.amk.photogenerator.ui.theme.PhotoGeneratorTheme
import com.amk.photogenerator.util.MyScreens
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    PhotoGeneratorTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            LoadingScreen()
        }
    }
}

@Composable
fun LoadingScreen() {
    val viewmodel = getNavViewModel<PhotoGeneratorViewModel>()
    val navigation = getNavController()

    BackHandler(enabled = true) {
        navigation.navigate(MyScreens.PhotoGeneratorScreen.route) {
            popUpTo(MyScreens.PhotoGeneratorScreen.route) {
                inclusive = true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        PhotoGeneratorAnimation()

        Text("ممکنه یکم طول بکشه!")
    }

    if (!viewmodel.loading.value) {
        navigation.navigate(MyScreens.PhotoResultScreen.route)
    }

}


@Composable
fun PhotoGeneratorAnimation() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.photogeneratorloading)
    )

    LottieAnimation(
        modifier = Modifier
            .size(270.dp)
            .padding(top = 16.dp, bottom = 36.dp),
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
}

