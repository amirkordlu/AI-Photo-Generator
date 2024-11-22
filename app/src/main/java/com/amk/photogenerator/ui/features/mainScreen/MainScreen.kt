package com.amk.photogenerator.ui.features.mainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        MainToolbar {
            navigation.navigate(MyScreens.LoginScreen.route) {
                popUpTo(MyScreens.MainScreen.route) {
                    inclusive = false
                }
            }
        }
    }
}


@Composable
fun MainToolbar(onProfileClicked: () -> Unit) {

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier
                .fillMaxSize()
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
