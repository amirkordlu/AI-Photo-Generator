package com.amk.photogenerator.ui.features.mainScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.amk.photogenerator.ui.theme.PhotoGeneratorTheme

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

}