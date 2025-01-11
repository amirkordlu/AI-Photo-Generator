package com.amk.negareh.ui.features.mainScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amk.negareh.R
import com.amk.negareh.ui.theme.PhotoGeneratorTheme
import com.amk.negareh.ui.theme.Typography
import com.amk.negareh.ui.theme.bodyLargeCard
import com.amk.negareh.ui.theme.bodyMediumCard
import com.amk.negareh.ui.theme.bodySmallCard
import com.amk.negareh.util.MyScreens
import com.amk.negareh.util.getCurrentTime
import dev.burnoo.cokoin.navigation.getNavController

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    PhotoGeneratorTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    val navigation = getNavController()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        MainToolbar {
            navigation.navigate(MyScreens.LoginScreen.route) {
                popUpTo(MyScreens.MainScreen.route) {
                    inclusive = false
                }
            }
        }

        MainCard(
            Modifier
                .align(Alignment.CenterHorizontally)
                .offset(y = (-110).dp), {
                navigation.navigate(MyScreens.PhotoGeneratorScreen.route)
            }, R.drawable.img_card_create, "متن به عکس", "!تصویرش کن", false
        )

        Spacer(modifier = Modifier.padding(vertical = 10.dp))

        MainCard(
            Modifier
                .align(Alignment.CenterHorizontally)
                .offset(y = (-110).dp),
            {
                Toast.makeText(context, "به‌زودی این سرویس به نگاره اضافه میشه", Toast.LENGTH_SHORT)
                    .show()
            },
            R.drawable.img_card_ocr,
            "عکس به متن", "!تبدیلش کن", true
        )

        Spacer(modifier = Modifier.padding(vertical = 10.dp))

        MainCard(
            Modifier
                .align(Alignment.CenterHorizontally)
                .offset(y = (-110).dp), {
                Toast.makeText(context, "به‌زودی این سرویس به نگاره اضافه میشه", Toast.LENGTH_SHORT)
                    .show()
            },
            R.drawable.img_card_enhance,
            "بهبود تصویر", "!تقویتش کن", true
        )

        Spacer(modifier = Modifier.padding(vertical = 10.dp))

        MainCard(
            Modifier
                .align(Alignment.CenterHorizontally)
                .offset(y = (-110).dp), {
                Toast.makeText(context, "به‌زودی این سرویس به نگاره اضافه میشه", Toast.LENGTH_SHORT)
                    .show()
            },
            R.drawable.img_card_change,
            "تغییر چهره", "!تعویضش کن", true
        )

    }

}


@Composable
fun MainToolbar(onProfileClicked: () -> Unit) {

    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(0.dp)),
            painter = painterResource(R.drawable.main_rectangle),
            contentScale = ContentScale.FillBounds,
            contentDescription = null
        )

        Column {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onProfileClicked.invoke() }) {
                    Image(
                        painter = painterResource(R.drawable.ic_account),
                        contentDescription = "Account"
                    )
                }

                Text(
                    text = "خوش اومدی",
                    style = Typography.bodyLarge,
                    fontSize = 32.sp,
                    color = Color.White,
                    modifier = Modifier.padding(end = 16.dp)
                )

            }

            Text(
                text = getCurrentTime(),
                style = Typography.bodyMedium,
                color = Color.White,
                fontSize = 26.sp,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .align(Alignment.End)
            )

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainCard(
    modifier: Modifier = Modifier,
    onCardClicked: () -> Unit,
    cardImage: Int,
    mainTextCard: String,
    secondaryTextCard: String,
    isSoon: Boolean
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 20.dp),
        onClick = { onCardClicked.invoke() }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            Image(
                painter = painterResource(cardImage),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End,
            ) {

                Text(
                    text = mainTextCard,
                    style = bodyLargeCard
                )

                Spacer(modifier = Modifier.padding(vertical = 6.dp))

                Text(
                    text = secondaryTextCard,
                    style = bodyMediumCard
                )
            }

            if (isSoon) {
                Box(
                    modifier =
                    Modifier
                        .background(
                            color = Color(0xFFEF5350),
                            shape = RoundedCornerShape(size = 6.dp)
                        )
                        .padding(start = 8.dp, top = 3.dp, end = 8.dp, bottom = 4.dp)
                ) {
                    Text(
                        "به‌زودی",
                        style = bodySmallCard
                    )

                }
            }
        }
    }
}
