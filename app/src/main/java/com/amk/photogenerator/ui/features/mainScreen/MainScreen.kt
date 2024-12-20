package com.amk.photogenerator.ui.features.mainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amk.photogenerator.R
import com.amk.photogenerator.ui.theme.PhotoGeneratorTheme
import com.amk.photogenerator.ui.theme.Typography
import com.amk.photogenerator.util.MyScreens
import com.amk.photogenerator.util.getCurrentTime
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
                .offset(y = (-60).dp)
        ) {
            navigation.navigate(MyScreens.PhotoGeneratorScreen.route)
        }

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
fun MainCard(modifier: Modifier = Modifier, onCardClicked: () -> Unit) {
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
                painter = painterResource(R.drawable.main_rectangle),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = "ساخت عکس",
                style = Typography.bodyLarge,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
                    .background(Color.Black.copy(alpha = 0.6f))
            )
        }
    }
}
