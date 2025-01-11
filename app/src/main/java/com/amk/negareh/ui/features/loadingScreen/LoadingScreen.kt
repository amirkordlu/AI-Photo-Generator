package com.amk.negareh.ui.features.loadingScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.amk.negareh.R
import com.amk.negareh.ui.features.photoGeneratorScreen.PhotoGeneratorViewModel
import com.amk.negareh.ui.theme.PhotoGeneratorTheme
import com.amk.negareh.ui.theme.Typography
import com.amk.negareh.util.MyScreens
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
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PhotoGeneratorAnimation()

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

            Text("نگاره در حال ساخت عکس شماست...", style = Typography.bodyLarge)


            Spacer(modifier = Modifier.padding(vertical = 12.dp))

            Text(
                modifier = Modifier.padding(bottom = 22.dp),
                text = "سرورهای نگاره مستقیما به ChatGPT متصل میشه و عکس نهایی همون عکس ساخته شده با Dall-E3 هست",
                style = Typography.bodyMedium,
                lineHeight = 28.sp
            )

        }
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
            .size(290.dp)
            .padding(top = 12.dp, bottom = 36.dp),
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
}

