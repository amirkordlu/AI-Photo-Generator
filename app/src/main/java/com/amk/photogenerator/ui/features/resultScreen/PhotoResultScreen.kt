package com.amk.photogenerator.ui.features.resultScreen

import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.toBitmap
import com.amk.photogenerator.R
import com.amk.photogenerator.model.data.PhotoGeneratorDallEResponse
import com.amk.photogenerator.ui.features.loginScreen.AccountViewModel
import com.amk.photogenerator.ui.features.photoGeneratorScreen.PhotoGeneratorViewModel
import com.amk.photogenerator.ui.theme.PhotoGeneratorTheme
import com.amk.photogenerator.ui.theme.Typography
import com.amk.photogenerator.util.MyScreens
import com.amk.photogenerator.util.copyTextToClipboard
import com.amk.photogenerator.util.downloadAndShareImage
import com.amk.photogenerator.util.getPrompt
import com.amk.photogenerator.util.saveBitmapToGallery
import com.amk.photogenerator.util.shareImage
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
    val context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val viewModel = getNavViewModel<PhotoGeneratorViewModel>()
    val navigation = getNavController()
    val generatedPromt = getPrompt(context).collectAsState(initial = "")
    val scope = rememberCoroutineScope()
    val accountViewModel: AccountViewModel = viewModel()
    val bitmapState = remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(Unit) {
        accountViewModel.loadPointsFromBazaar(context, lifecycleOwner)
    }

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
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GeneratedPhoto(context, lifecycleOwner, viewModel.generatedPhoto.value, accountViewModel) {
            bitmapState.value = it
        }

        Spacer(modifier = Modifier.padding(vertical = 16.dp))

        if (generatedPromt.value?.isNotEmpty() == true) {
            Text(
                text = generatedPromt.value.toString(), style = Typography.bodyMedium,
                modifier = Modifier
                    .clickable {
                        copyTextToClipboard(context, generatedPromt.value.toString())
                    }
                    .padding(horizontal = 12.dp)
            )
        }

        Spacer(modifier = Modifier.padding(vertical = 16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            IconButton(onClick = {
                scope.launch(Dispatchers.IO) {
                    val photoUrl = viewModel.generatedPhoto.value.data[0].url
                    val imageUri = downloadAndShareImage(context, photoUrl)
                    if (imageUri != null) {
                        shareImage(context, imageUri)
                    }
                }
                Toast.makeText(context, "یکم صبر کن", Toast.LENGTH_SHORT).show()
            }) {
                Image(
                    modifier = Modifier.size(28.dp),
                    painter = painterResource(R.drawable.ic_share),
                    contentDescription = "Share"
                )
            }

            GenerateButton("دوباره بساز") {
                navigation.navigate(MyScreens.PhotoGeneratorScreen.route) {
                    popUpTo(MyScreens.PhotoGeneratorScreen.route) {
                        inclusive = true
                    }
                }
            }

            IconButton(onClick = {
                bitmapState.value?.let { saveBitmapToGallery(context, it, "Negareh") }
            }) {
                Image(
                    modifier = Modifier.size(28.dp),
                    painter = painterResource(R.drawable.ic_download),
                    contentDescription = "Save"
                )
            }

        }
    }
}

@Composable
fun GeneratedPhoto(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    photo: PhotoGeneratorDallEResponse,
    accountViewModel: AccountViewModel,
    onBitmapReady: (Bitmap) -> Unit
) {
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
            placeholder = painterResource(R.drawable.img_photoplaceholder),
            onSuccess = {
                val bitmap = it.result.image.toBitmap()
                onBitmapReady(bitmap)
                accountViewModel.subtractPoints(context, lifecycleOwner, 1, onError = {
                    Toast.makeText(
                        context,
                        "خطا در کسر امتیاز",
                        Toast.LENGTH_SHORT
                    ).show()
                })
            },
            onError = {
                Toast.makeText(
                    context,
                    "خطا در ساخت عکس...نگران نباش چیزی از امتیازهات کم نمیشه :)",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
    }
}

@Composable
fun GenerateButton(buttonText: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth(0.75f)
            .height(58.dp),
        onClick = { onClick.invoke() },
        shape = RoundedCornerShape(36.dp),
        colors = ButtonDefaults.buttonColors(Color(0xFF7E84F9))
    ) {

        Icon(painterResource(R.drawable.ic_majic), null, modifier = Modifier.size(22.dp))

        Spacer(modifier = Modifier.padding(end = 8.dp))

        Text(
            text = buttonText, style = Typography.bodyMedium, color = Color.White, fontSize = 16.sp
        )

    }

}