package com.amk.photogenerator.ui.features.loginScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.amk.photogenerator.R
import com.amk.photogenerator.ui.theme.PhotoGeneratorTheme
import com.amk.photogenerator.ui.theme.Typography
import com.amk.photogenerator.util.MyScreens
import dev.burnoo.cokoin.navigation.getNavController

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    PhotoGeneratorTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            LoginScreen()
        }
    }
}

@Composable
fun LoginScreen() {
    val navigation = getNavController()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Welcome()
        LoginSection(
            onLoginClicked = { navigation.navigate(MyScreens.OTPScreen.route) },
            onSignUpClicked = { navigation.navigate(MyScreens.SignUpScreen.route) })
        LoginWithAccounts()
    }
}

@Composable
fun Welcome() {
    Column(
        modifier = Modifier.padding(bottom = 48.dp, top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        MainAnimation()

    }
}

@Composable
fun LoginSection(onLoginClicked: () -> Unit, onSignUpClicked: () -> Unit) {
    Column(modifier = Modifier.padding(bottom = 32.dp)) {
        Button(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .padding(top = 8.dp, bottom = 16.dp)
                .height(56.dp),
            onClick = { onLoginClicked.invoke() },
            shape = RoundedCornerShape(36.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF7853A1))
        ) {
            Text(
                text = "ورود",
                style = Typography.bodyMedium,
                color = Color.White
            )

        }

        Button(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .padding(top = 8.dp, bottom = 16.dp)
                .height(56.dp),
            onClick = { onSignUpClicked.invoke() },
            shape = RoundedCornerShape(36.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFFAF6FF))
        ) {
            Text(
                text = "ثبت‌نام",
                style = Typography.bodyMedium,
                color = Color(0xFF7853A1)
            )

        }
    }
}

@Composable
fun LoginWithAccounts() {
    Column(
        modifier = Modifier.padding(bottom = 50.dp, top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Divider(
                color = Color(151, 151, 151, 255),
                thickness = 0.5.dp,
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .padding(end = 8.dp)
            )

            Text(
                text = "ورود با",
                color = Color(0xFF424242),
                style = Typography.bodySmall
            )

            Divider(
                color = Color(151, 151, 151),
                thickness = 0.5.dp,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.padding(4.dp))

        IconButton(onClick = {}) {
            Icon(painter = painterResource(R.drawable.ic_google), contentDescription = null)
        }

    }
}

@Composable
fun MainAnimation() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.signup_animation)
    )

    LottieAnimation(
        modifier = Modifier
            .size(270.dp)
            .padding(top = 16.dp, bottom = 36.dp),
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
}

