package com.amk.photogenerator.ui.features.photoGeneratorScreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amk.photogenerator.R
import com.amk.photogenerator.ui.theme.PhotoGeneratorTheme
import com.amk.photogenerator.ui.theme.Typography
import com.amk.photogenerator.ui.theme.textFieldStyle
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
    val viewModel = getNavViewModel<PhotoGeneratorViewModel>()
    val navigation = getNavController()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val promt = mainTextField("توصیف کن چی بسازم برات؟")

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        GenerateButton("بساز") {
            if (promt.isNotEmpty()) {
                Toast.makeText(context, promt, Toast.LENGTH_SHORT).show()
                viewModel.photoGenerator(promt)
            } else {
                Toast.makeText(context, "چیزیو خالی نذار", Toast.LENGTH_SHORT).show()
            }
        }

        if (viewModel.loading.value) {
            navigation.navigate(MyScreens.LoadingScreen.route)
        }
    }

    Toast.makeText(context, viewModel.generatedPhoto.value.data.toString(), Toast.LENGTH_SHORT)
        .show()

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mainTextField(hint: String): String {
    var text = remember { mutableStateOf(TextFieldValue("")) }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(0.75f),
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
    return text.value.text
}

@Composable
fun GenerateButton(buttonText: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth(0.75f)
            .padding(top = 8.dp, bottom = 16.dp)
            .height(56.dp),
        onClick = { onClick.invoke() },
        shape = RoundedCornerShape(36.dp),
        colors = ButtonDefaults.buttonColors(Color(0xFF0EA960))
    ) {

        Icon(painterResource(R.drawable.ic_majic), null, modifier = Modifier.size(24.dp))

        Spacer(modifier = Modifier.padding(end = 8.dp))

        Text(
            text = buttonText,
            style = Typography.bodyMedium,
            color = Color.White
        )

    }

}