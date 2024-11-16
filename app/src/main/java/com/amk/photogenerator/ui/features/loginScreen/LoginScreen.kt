package com.amk.photogenerator.ui.features.loginScreen

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.LocalActivityResultRegistryOwner
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.amk.photogenerator.R
import com.amk.photogenerator.ui.theme.PhotoGeneratorTheme
import com.amk.photogenerator.ui.theme.Typography
import com.amk.photogenerator.util.RSA_KEY

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
    val context = LocalContext.current
    val activity = LocalContext.current as Activity
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val activityResultRegistry = LocalActivityResultRegistryOwner.current!!.activityResultRegistry

    val viewModel: AccountViewModel = viewModel()
    val paymentViewModel: PaymentViewModel = viewModel()



    LaunchedEffect(Unit) {
        paymentViewModel.initSecurityCheck(RSA_KEY)
        paymentViewModel.initPaymentConfiguration()
        paymentViewModel.initPayment(context)
        paymentViewModel.connectToBazaar(
            onSuccess = {
                // اتصال موفق
                Log.v("LoginScreen", "Connected to Bazaar")
            },
            onFailure = { throwable ->
                // خطا در اتصال
                Log.v("LoginScreen", "Failed to connect: ${throwable.message}")
            },
            onDisconnected = {
                // قطع اتصال
                Log.v("LoginScreen", "Disconnected from Bazaar")
            }
        )
    }

    // Get login and points
    viewModel.getBazaarLogin(context, lifecycleOwner)
    viewModel.loadPointsFromBazaar(context, lifecycleOwner)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainAnimation()

        Button(onClick = {
            paymentViewModel.startPurchase(
                "10point",
                "purchasePayload",
                activityResultRegistry,
                onFailure = { Toast.makeText(context, "ناموفق", Toast.LENGTH_SHORT).show() },
                onSuccess = { purchaseEntity ->
                    viewModel.addPoints(context, lifecycleOwner, 10)
                    paymentViewModel.consumePurchase(purchaseEntity.purchaseToken, {
                        Toast.makeText(
                            context,
                            "خرید موفق" + purchaseEntity.purchaseToken,
                            Toast.LENGTH_SHORT
                        ).show()
                    }, {})
                })
        }) {
            Text("خرید")
        }

        BazaarButton {
            viewModel.getBazaarLogin(context, lifecycleOwner)
            if (viewModel.hasLogin.value) {
                Toast.makeText(context, "شما وارد حساب کاربری خود شده‌اید", Toast.LENGTH_SHORT)
                    .show()
            } else {
                viewModel.signInBazaar(context, activity, lifecycleOwner)
            }
        }

        Text(text = "امتیازات فعلی: ${viewModel.points.value}")

        PointsSection(addPointClicked = {
            viewModel.addPoints(context, lifecycleOwner, 10)
        },
            subtractPointClicked = {
                viewModel.subtractPoints(context, lifecycleOwner, 2)
            })

        LoginWithAccounts()

    }
}

@Composable
fun BazaarButton(signInClicked: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth(0.75f)
            .padding(top = 8.dp, bottom = 16.dp)
            .height(56.dp),
        onClick = { signInClicked.invoke() },
        shape = RoundedCornerShape(36.dp),
        colors = ButtonDefaults.buttonColors(Color(0xFF0EA960))
    ) {

        Icon(painterResource(R.drawable.ic_bazaar), null, modifier = Modifier.size(34.dp))

        Text(
            text = "ورود با بازار",
            style = Typography.bodyMedium,
            color = Color.White
        )

    }
}

@Composable
fun PointsSection(addPointClicked: () -> Unit, subtractPointClicked: () -> Unit) {
    Column(modifier = Modifier.padding(bottom = 32.dp)) {
        Button(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .padding(top = 8.dp, bottom = 16.dp)
                .height(56.dp),
            onClick = { addPointClicked.invoke() },
            shape = RoundedCornerShape(36.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF7853A1))
        ) {
            Text(
                text = "اضافه کردن سکه",
                style = Typography.bodyMedium,
                color = Color.White
            )

        }

        Button(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .padding(top = 8.dp, bottom = 16.dp)
                .height(56.dp),
            onClick = { subtractPointClicked.invoke() },
            shape = RoundedCornerShape(36.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFFAF6FF))
        ) {
            Text(
                text = "کاهش سکه",
                style = Typography.bodyMedium,
                color = Color(0xFF7853A1)
            )

        }
    }
}

@Composable
fun LoginWithAccounts() {
    val context = LocalContext.current

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

        IconButton(onClick = { Toast.makeText(context, "بزودی!", Toast.LENGTH_SHORT).show() }) {
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

