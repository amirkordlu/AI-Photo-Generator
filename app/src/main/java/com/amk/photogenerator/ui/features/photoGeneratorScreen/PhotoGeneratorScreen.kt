package com.amk.photogenerator.ui.features.photoGeneratorScreen

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.amk.photogenerator.R
import com.amk.photogenerator.ui.features.loginScreen.AccountViewModel
import com.amk.photogenerator.ui.theme.PhotoGeneratorTheme
import com.amk.photogenerator.ui.theme.Typography
import com.amk.photogenerator.ui.theme.bodySmallCard
import com.amk.photogenerator.ui.theme.textFieldStyle
import com.amk.photogenerator.util.FirstRunPreferences
import com.amk.photogenerator.util.FirstRunPreferences.isFirstRun
import com.amk.photogenerator.util.MyScreens
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel

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
    val isFirstRun: Boolean = isFirstRun(context)

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
                .padding(start = 12.dp)
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
                text = when (val currentPoints = accountViewModel.points.value) {
                    null -> "..."
                    else -> {
                        if (isFirstRun) {
                            Toast.makeText(
                                context,
                                "به مناسبت ورودت به نگاره ۳ تا سکه مهمون ما باش :)",
                                Toast.LENGTH_SHORT
                            ).show()
                            FirstRunPreferences.setFirstRunCompleted(context)
                            accountViewModel.addPoints(context, lifecycleOwner, 3)
                        }
                        currentPoints.toString()
                    }
                }, style = Typography.bodyMedium,
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, end = 12.dp)
            )

        }


        Column {


            val promt = mainTextField("توصیف کن چی بسازم برات...")



            Spacer(modifier = Modifier.padding(vertical = 16.dp))

            GenerateButton("بساز") {
                if (promt.isNotEmpty()) {
                    viewModel.photoGenerator(promt)
                } else {
                    Toast.makeText(context, "چیزیو خالی نذار", Toast.LENGTH_SHORT).show()
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

@OptIn(ExperimentalMaterial3Api::class)
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