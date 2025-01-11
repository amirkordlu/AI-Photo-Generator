package com.amk.negareh.ui.features.photoGeneratorScreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.amk.negareh.R
import com.amk.negareh.ui.features.loginScreen.AccountViewModel
import com.amk.negareh.ui.theme.PhotoGeneratorTheme
import com.amk.negareh.ui.theme.Typography
import com.amk.negareh.ui.theme.bodySmallCard
import com.amk.negareh.ui.theme.textFieldStyle
import com.amk.negareh.util.MyScreens
import com.amk.negareh.util.NetworkChecker
import com.amk.negareh.util.savePrompt
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    PhotoGeneratorTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            PhotoGeneratorScreen()
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun PhotoGeneratorScreen() {
    val context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val viewModel = getNavViewModel<PhotoGeneratorViewModel>()
    val accountViewModel: AccountViewModel = viewModel()
    val navigation = getNavController()
    val scope = rememberCoroutineScope()

    accountViewModel.getBazaarLogin(context, lifecycleOwner)
    accountViewModel.loadPointsFromBazaar(context, lifecycleOwner)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF5F7FA))
            .verticalScroll(rememberScrollState())
            .padding(vertical = 12.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            Modifier
                .align(Alignment.Start)
                .padding(start = 12.dp, end = 12.dp)
                .background(
                    color = Color(0xFF7E84F9),
                    shape = RoundedCornerShape(size = 16.dp)
                )
                .clickable(onClick = {
                    navigation.navigate(MyScreens.LoginScreen.route)
                }),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Image(
                painterResource(R.drawable.ic_coin),
                null,
                modifier = Modifier
                    .size(44.dp)
                    .padding(start = 8.dp, top = 4.dp, bottom = 4.dp, end = 4.dp)
            )

            Text(
                text = when {
                    !accountViewModel.hasLogin.value -> {
                        "برای دیدن امتیازهات نیازه وارد حساب بازارت بشی"
                    }

                    accountViewModel.points.value == null -> {
                        "...در حال بارگذاری"
                    }

                    else -> {
                        val currentPoints = accountViewModel.points.value
                        "$currentPoints"
                    }
                },
                style = Typography.bodyMedium,
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, end = 12.dp)
            )

        }


        Column {


            val promt = mainTextField("توصیف کن چی بسازم برات...")



            Spacer(modifier = Modifier.padding(vertical = 16.dp))

            GenerateButton("بساز") {
                if (promt.isEmpty()) {
                    Toast.makeText(context, "هنوز نگفتی چی بسازم برات :(", Toast.LENGTH_SHORT)
                        .show()
                } else if (promt.isEmpty() && !accountViewModel.hasLogin.value) {
                    Toast.makeText(context, "اول وارد حساب بازارت شو :)", Toast.LENGTH_SHORT)
                        .show()
                } else if (!accountViewModel.hasLogin.value) {
                    Toast.makeText(context, "هنوز وارد حساب بازارت نشدی :(", Toast.LENGTH_SHORT)
                        .show()
                } else if (accountViewModel.points.value == null) {
                    Toast.makeText(context, "بذار اول امتیازهات بارگذاری بشه", Toast.LENGTH_SHORT)
                        .show()
                } else if (!NetworkChecker(context).isInternetConnected) {
                    Toast.makeText(context, "اینترنت نداری :(", Toast.LENGTH_SHORT).show()
                } else if (accountViewModel.points.value == 0) {
                    Toast.makeText(context, "سکه‌هات کافی نیست :(", Toast.LENGTH_SHORT).show()
                } else {
                    scope.launch {
                        savePrompt(context, promt)
                    }
                    viewModel.photoGenerator(promt, onError = {
                        Toast.makeText(context, "خطا در ساخت عکس", Toast.LENGTH_SHORT).show()
                    })
                }
            }


        }

        Text(
            "\uD83D\uDD25Dall-E3 قدرت گرفته از",
            style = bodySmallCard,
            color = Color(0xFF424242)
        )

        if (viewModel.loading.value) {
            navigation.navigate(MyScreens.LoadingScreen.route)
        }
    }

}

@Composable
fun mainTextField(hint: String): String {
    var text = remember { mutableStateOf(TextFieldValue("")) }
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .height(210.dp),
            value = text.value,
            onValueChange = { text.value = it },
            textStyle = textFieldStyle,
            placeholder = {
                Text(
                    text = hint,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Right,
                    style = textFieldStyle
                )
            },
            shape = RoundedCornerShape(14.dp),
            singleLine = false,
            maxLines = 10,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
        )
    }
    return text.value.text
}

@Composable
fun GenerateButton(buttonText: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth(0.75f)
            .height(76.dp),
        onClick = { onClick.invoke() },
        shape = RoundedCornerShape(36.dp),
        colors = ButtonDefaults.buttonColors(Color(0xFF7E84F9))
    ) {

        Icon(painterResource(R.drawable.ic_majic), null, modifier = Modifier.size(24.dp))

        Spacer(modifier = Modifier.padding(end = 8.dp))

        Text(
            text = buttonText,
            style = Typography.bodyMedium,
            color = Color.White,
            fontSize = 18.sp
        )

    }

}