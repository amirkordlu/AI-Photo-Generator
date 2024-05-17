package com.amk.photogenerator.util

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.platform.TextToolbar
import androidx.compose.ui.platform.TextToolbarStatus
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.amk.photogenerator.ui.theme.PhotoGeneratorTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OutlinedOtpTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    length: Int = 6,
    onFilled: () -> Unit = {},
    errorMessage: String? = null,
    helperText: String? = null,
    helperTextColor: Color = MaterialTheme.colorScheme.onSurface,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge.copy(
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurface
    ),
    enabled: Boolean = true,
    readOnly: Boolean = false,
    visualTransformation: VisualTransformation = PasswordVisualTransformation(),
    requestFocus: Boolean,
    clearFocusWhenFilled: Boolean
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val updatedOnValueChange by rememberUpdatedState(onValueChange)
    val updatedOnFilled by rememberUpdatedState(onFilled)

    val code by remember(value) {
        mutableStateOf(TextFieldValue(value, TextRange(value.length)))
    }

    DisposableEffect(requestFocus) {
        if (requestFocus) {
            focusRequester.requestFocus()
        }
        onDispose { }
    }

    Column(modifier = modifier) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            val customTextSelectionColors = TextSelectionColors(
                handleColor = Color.Transparent,
                backgroundColor = Color.Transparent,
            )

            CompositionLocalProvider(
                LocalTextToolbar provides EmptyTextToolbar,
                LocalTextSelectionColors provides customTextSelectionColors
            ) {
                BasicTextField(
                    modifier = Modifier
                        .focusRequester(focusRequester = focusRequester)
                        .fillMaxWidth(),
                    value = code,
                    onValueChange = {
                        if (!it.text.isDigitsOnly() || it.text.length > length)
                            return@BasicTextField

                        updatedOnValueChange(it.text)

                        if (it.text.length == length) {
                            keyboardController?.hide()
                            if (clearFocusWhenFilled) {
                                focusRequester.freeFocus()
                                focusManager.clearFocus()
                            }
                            updatedOnFilled()
                        }
                    },
                    visualTransformation = visualTransformation,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.NumberPassword
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    ),
                    textStyle = textStyle,
                    enabled = enabled,
                    readOnly = readOnly,
                    decorationBox = {
                        OtpInputDecoration(
                            code = code.text,
                            length = length,
                            textStyle = textStyle,
                            enabled = enabled,
                            isError = !errorMessage.isNullOrBlank(),
                            visualTransformation = visualTransformation
                        )
                    }
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp)
        ) {
            Text(
                text = errorMessage ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .alpha(if (!errorMessage.isNullOrBlank()) 1f else 0f)
            )

            if (helperText != null && errorMessage.isNullOrBlank()) {
                Text(
                    text = helperText,
                    textAlign = TextAlign.Center,
                    color = helperTextColor,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun OtpInputDecoration(
    modifier: Modifier = Modifier,
    code: String,
    length: Int,
    textStyle: TextStyle,
    enabled: Boolean,
    isError: Boolean,
    visualTransformation: VisualTransformation
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.Center),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            for (i in 0 until length) {
                val text = if (i < code.length) code[i].toString() else ""
                OtpEntry(
                    modifier = Modifier.weight(1f, fill = false),
                    text = text,
                    textStyle = textStyle,
                    enabled = enabled,
                    isError = isError,
                    visualTransformation = visualTransformation
                )
            }
        }
    }
}

@Composable
private fun OtpEntry(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle,
    enabled: Boolean,
    isError: Boolean,
    visualTransformation: VisualTransformation
) {
    val transformedText = remember(text, visualTransformation) {
        visualTransformation.filter(AnnotatedString(text))
    }.text.text

    val borderColor by animateColorAsState(
        targetValue = when {
            isError -> MaterialTheme.colorScheme.error
            !enabled -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5F)
            transformedText.isNotEmpty() -> MaterialTheme.colorScheme.primary
            else -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5F)
        },
        label = "textColor"
    )

    Box(
        modifier = modifier
            .width(42.dp)
            .height(48.dp)
            .border(width = 2.dp, color = borderColor, shape = RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        val textColor by animateColorAsState(
            targetValue = when {
                isError -> MaterialTheme.colorScheme.error
                !enabled -> textStyle.color.copy(alpha = 0.5F)

                else -> textStyle.color
            },
            label = "textColor"
        )

        AnimatedContent(
            modifier = Modifier.fillMaxWidth(),
            targetState = transformedText,
            transitionSpec = {
                ContentTransform(
                    targetContentEnter = slideInVertically(initialOffsetY = { it / 2 }) + fadeIn(),
                    initialContentExit = slideOutVertically(targetOffsetY = { it / 2 }) + fadeOut(),
                    sizeTransform = null
                )
            },
            contentAlignment = Alignment.Center,
            label = "textVisibility"
        ) { text ->
            if (text.isNotBlank()) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = text,
                    color = textColor,
                    style = textStyle
                )
            }
        }
    }
}

private object EmptyTextToolbar : TextToolbar {
    override val status: TextToolbarStatus = TextToolbarStatus.Hidden

    override fun hide() {}

    override fun showMenu(
        rect: Rect,
        onCopyRequested: (() -> Unit)?,
        onPasteRequested: (() -> Unit)?,
        onCutRequested: (() -> Unit)?,
        onSelectAllRequested: (() -> Unit)?,
    ) {
    }
}

@Preview
@Composable
private fun OutlinedOtpTextFieldPreview() {
    PhotoGeneratorTheme {
        Surface {
            Column {
                var text by remember { mutableStateOf("") }

                OutlinedOtpTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = text,
                    onValueChange = { text = it },
                    requestFocus = true,
                    clearFocusWhenFilled = true
                )
            }
        }
    }
}
