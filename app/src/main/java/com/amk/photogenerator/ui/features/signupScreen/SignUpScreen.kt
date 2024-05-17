package com.amk.photogenerator.ui.features.signupScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    PhotoGeneratorTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            SignUpScreen()
        }
    }
}

@Composable
fun SignUpScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        IconButton(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 8.dp, start = 8.dp),
            onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = null
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .padding(bottom = 18.dp, end = 18.dp)
                    .align(Alignment.End),
                text = "ثبت نام در نگاره",
                style = Typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = Color(66, 66, 66)
            )

            SignUpInputs("نام", "مهدی")
            Spacer(modifier = Modifier.padding(12.dp))
            SignUpInputs("نام خانوادگی", "وارد کنید")
            Spacer(modifier = Modifier.padding(12.dp))
            SignUpInputs("شماره همراه", "وارد کنید")

        }

        Column(
            modifier = Modifier.padding(bottom = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Button(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(top = 8.dp, bottom = 20.dp)
                    .height(56.dp),
                onClick = { },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF7853A1))
            ) {
                Text(
                    text = "ادامه",
                    style = Typography.bodyMedium,
                    color = Color.White
                )

            }

            Divider(
                color = Color(151, 151, 151),
                thickness = 0.5.dp,
                modifier = Modifier
                    .fillMaxWidth(0.88f)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 22.dp, bottom = 8.dp, top = 8.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {

                TextButton(onClick = { }) {
                    Text(
                        text = "وارد شو",
                        style = Typography.bodySmall,
                        color = Color(159, 222, 26),
                        textAlign = TextAlign.End
                    )
                }

                Text(text = "قبلا ثبت نام کردی؟", style = Typography.bodySmall)

            }

        }

    }

}

@Composable
fun SignUpInputs(label: String, hint: String) {
    Column(horizontalAlignment = Alignment.End) {

        Text(
            text = label,
            style = Typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp),
            color = Color(48, 48, 48)
        )

        mainTextField(hint = hint)
    }
}

@Composable
fun mainTextField(hint: String): String {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(0.9f),
        value = text,
        onValueChange = { text = it },
        textStyle = textFieldStyle,
        placeholder = {
            Text(
                text = hint,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Right,
                style = textFieldStyle,
                color = Color(66, 66, 66)
            )
        },
        shape = RoundedCornerShape(20.dp),
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Next
        ),
    )
    return text.text
}

