package com.amk.photogenerator.ui.features.resultScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.amk.photogenerator.R
import com.amk.photogenerator.model.data.PhotoGeneratorDallEResponse
import com.amk.photogenerator.ui.features.photoGeneratorScreen.PhotoGeneratorViewModel
import com.amk.photogenerator.ui.theme.PhotoGeneratorTheme
import com.amk.photogenerator.util.MyScreens
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel

@Preview(showBackground = true)
@Composable
fun PhotoResultScreenPreview() {
    PhotoGeneratorTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            PhotoResultScreen()
        }
    }
}

@Composable
fun PhotoResultScreen() {
    val viewModel = getNavViewModel<PhotoGeneratorViewModel>()
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
        GeneratedPhoto(viewModel.generatedPhoto.value)
    }
}

@Composable
fun GeneratedPhoto(photo: PhotoGeneratorDallEResponse) {
    if (photo.data.isNotEmpty()) {
        AsyncImage(
            model = photo.data[0].url,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                .clip(RoundedCornerShape(16.dp)),
            placeholder = painterResource(R.drawable.main_rectangle)
        )
    }
}