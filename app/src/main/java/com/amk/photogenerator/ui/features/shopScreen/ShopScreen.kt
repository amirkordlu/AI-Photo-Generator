package com.amk.photogenerator.ui.features.shopScreen

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivityResultRegistryOwner
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.amk.photogenerator.ui.features.loginScreen.AccountViewModel
import com.amk.photogenerator.ui.features.loginScreen.PaymentViewModel
import com.amk.photogenerator.ui.theme.PhotoGeneratorTheme
import com.amk.photogenerator.ui.theme.Typography
import com.amk.photogenerator.util.FirstRunPreferences
import com.amk.photogenerator.util.MyScreens
import com.amk.photogenerator.util.RSA_KEY
import dev.burnoo.cokoin.navigation.getNavController

@Preview(showBackground = true)
@Composable
fun ShopScreenPreview() {
    PhotoGeneratorTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            ShopScreen()
        }
    }
}

@Composable
fun ShopScreen() {
    val navigation = getNavController()
    BackHandler(enabled = true) {
        navigation.navigate(MyScreens.MainScreen.route) {
            popUpTo(MyScreens.MainScreen.route) {
                inclusive = true
            }
        }
    }
    val context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val activityResultRegistry =
        LocalActivityResultRegistryOwner.current!!.activityResultRegistry

    val viewModel: AccountViewModel = viewModel()
    val paymentViewModel: PaymentViewModel = viewModel()
    val isFirstRun: Boolean = FirstRunPreferences.isFirstRun(context)

    LaunchedEffect(Unit) {
        paymentViewModel.initSecurityCheck(RSA_KEY)
        paymentViewModel.initPaymentConfiguration()
        paymentViewModel.initPayment(context)
        paymentViewModel.connectToBazaar(
            onSuccess = {
                Log.v("LoginScreen", "Connected to Bazaar")
            },
            onFailure = { throwable ->
                Log.v("LoginScreen", "Failed to connect: ${throwable.message}")
            },
            onDisconnected = {
                Log.v("LoginScreen", "Disconnected from Bazaar")
            }
        )
        // Get login and points
        viewModel.getBazaarLogin(context, lifecycleOwner)
        viewModel.loadPointsFromBazaar(context, lifecycleOwner)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(onClick = {
            if (viewModel.points.value != null && viewModel.hasLogin.value) {
                paymentViewModel.startPurchase(
                    "10point",
                    "purchasePayload",
                    activityResultRegistry,
                    onFailure = {
                        Toast.makeText(context, "ناموفق", Toast.LENGTH_SHORT).show()
                    },
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
            } else {
                Toast.makeText(
                    context,
                    "لطفا ابتدا وارد حساب کاربری خود شوید",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }) {
            Text("خرید 10 امتیاز")
        }

        Button(onClick = {
            if (viewModel.points.value != null && viewModel.hasLogin.value) {
                paymentViewModel.startPurchase(
                    "30point",
                    "purchasePayload",
                    activityResultRegistry,
                    onFailure = {
                        Toast.makeText(context, "ناموفق", Toast.LENGTH_SHORT).show()
                    },
                    onSuccess = { purchaseEntity ->
                        viewModel.addPoints(context, lifecycleOwner, 30)
                        paymentViewModel.consumePurchase(purchaseEntity.purchaseToken, {
                            Toast.makeText(
                                context,
                                "خرید موفق" + purchaseEntity.purchaseToken,
                                Toast.LENGTH_SHORT
                            ).show()
                        }, {})
                    })
            } else {
                Toast.makeText(
                    context,
                    "لطفا ابتدا وارد حساب کاربری خود شوید",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }) {
            Text("خرید 30 امتیاز")
        }

        Button(onClick = {
            if (viewModel.points.value != null && viewModel.hasLogin.value) {
                paymentViewModel.startPurchase(
                    "60point",
                    "purchasePayload",
                    activityResultRegistry,
                    onFailure = {
                        Toast.makeText(context, "ناموفق", Toast.LENGTH_SHORT).show()
                    },
                    onSuccess = { purchaseEntity ->
                        viewModel.addPoints(context, lifecycleOwner, 60)
                        paymentViewModel.consumePurchase(purchaseEntity.purchaseToken, {
                            Toast.makeText(
                                context,
                                "خرید موفق" + purchaseEntity.purchaseToken,
                                Toast.LENGTH_SHORT
                            ).show()
                        }, {})
                    })
            } else {
                Toast.makeText(
                    context,
                    "لطفا ابتدا وارد حساب کاربری خود شوید",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }) {
            Text("خرید 60 امتیاز")
        }

        Button(onClick = {
            if (viewModel.points.value != null && viewModel.hasLogin.value) {
                paymentViewModel.startPurchase(
                    "150point",
                    "purchasePayload",
                    activityResultRegistry,
                    onFailure = {
                        Toast.makeText(context, "ناموفق", Toast.LENGTH_SHORT).show()
                    },
                    onSuccess = { purchaseEntity ->
                        viewModel.addPoints(context, lifecycleOwner, 150)
                        paymentViewModel.consumePurchase(purchaseEntity.purchaseToken, {
                            Toast.makeText(
                                context,
                                "خرید موفق" + purchaseEntity.purchaseToken,
                                Toast.LENGTH_SHORT
                            ).show()
                        }, {})
                    })
            } else {
                Toast.makeText(
                    context,
                    "لطفا ابتدا وارد حساب کاربری خود شوید",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }) {
            Text("خرید 150 امتیاز")
        }

        Button(onClick = {
            if (viewModel.points.value != null && viewModel.hasLogin.value) {
                paymentViewModel.startPurchase(
                    "300point",
                    "purchasePayload",
                    activityResultRegistry,
                    onFailure = {
                        Toast.makeText(context, "ناموفق", Toast.LENGTH_SHORT).show()
                    },
                    onSuccess = { purchaseEntity ->
                        viewModel.addPoints(context, lifecycleOwner, 300)
                        paymentViewModel.consumePurchase(purchaseEntity.purchaseToken, {
                            Toast.makeText(
                                context,
                                "خرید موفق" + purchaseEntity.purchaseToken,
                                Toast.LENGTH_SHORT
                            ).show()
                        }, {})
                    })
            } else {
                Toast.makeText(
                    context,
                    "لطفا ابتدا وارد حساب کاربری خود شوید",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }) {
            Text("خرید 300 امتیاز")
        }

        Button(onClick = {
            if (viewModel.points.value != null && viewModel.hasLogin.value) {
                paymentViewModel.startPurchase(
                    "500point",
                    "purchasePayload",
                    activityResultRegistry,
                    onFailure = {
                        Toast.makeText(context, "ناموفق", Toast.LENGTH_SHORT).show()
                    },
                    onSuccess = { purchaseEntity ->
                        viewModel.addPoints(context, lifecycleOwner, 500)
                        paymentViewModel.consumePurchase(purchaseEntity.purchaseToken, {
                            Toast.makeText(
                                context,
                                "خرید موفق" + purchaseEntity.purchaseToken,
                                Toast.LENGTH_SHORT
                            ).show()
                        }, {})
                    })
            } else {
                Toast.makeText(
                    context,
                    "لطفا ابتدا وارد حساب کاربری خود شوید",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }) {
            Text("خرید 500 امتیاز")
        }


        Text(
            text = when (val currentPoints = viewModel.points.value) {
                null -> "در حال بارگذاری..."
                else -> {
                    if (isFirstRun) {
                        Toast.makeText(
                            context,
                            "به مناسبت ورودت به نگاره ۳ تا سکه مهمون ما باش :)",
                            Toast.LENGTH_SHORT
                        ).show()
                        FirstRunPreferences.setFirstRunCompleted(context)
                        viewModel.addPoints(context, lifecycleOwner, 3)
                    }
                    "امتیاز فعلی: $currentPoints"
                }
            },
            style = Typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp)
        )

    }
}


