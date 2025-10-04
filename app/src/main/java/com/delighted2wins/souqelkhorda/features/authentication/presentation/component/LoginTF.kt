package com.delighted2wins.souqelkhorda.features.login.presentation.component


import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.delighted2wins.souqelkhorda.R

@Composable
fun LoginPasswordTF(
    txt: String = stringResource(R.string.password),
    onValueChange: (String) -> Unit = {},
    horizentalPading : Int = 16
) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val colors = MaterialTheme.colorScheme

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val borderWidth by animateDpAsState(
        targetValue = if (isFocused) 2.dp else 1.dp,
        animationSpec = tween(durationMillis = 300)
    )
    val borderColor by animateColorAsState(
        targetValue = if (isFocused) colors.primary else colors.outline.copy(alpha = 0.5f),
        animationSpec = tween(durationMillis = 300)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizentalPading.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = txt,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 8.dp),
            color = colors.onBackground,
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .border(
                    width = borderWidth,
                    color = borderColor,
                    shape = RoundedCornerShape(12.dp)
                ),
            value = text,
            visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
            placeholder = {
                Text(
                    text = txt,
                    fontSize = 14.sp
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus(force = true)
                    keyboardController?.hide()
                }
            ),
            onValueChange = { it ->
                text = it
                onValueChange(it.text)
            },
            singleLine = true,
            maxLines = 1,
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colors.surface,
                unfocusedContainerColor = colors.surface,
                cursorColor = colors.primary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = colors.onBackground,
                unfocusedTextColor = colors.onBackground
            ),
            interactionSource = interactionSource
,
                    trailingIcon = {
                IconButton(onClick = { passwordHidden = !passwordHidden }) {
                    Icon(
                        imageVector = if (passwordHidden) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                        contentDescription = if (passwordHidden) "Show password" else "Hide password"
                    )
                }
            }
        )
    }
}


@Composable
fun LoginTF(
    txt: String = stringResource(R.string.email),
    onValueChange: (String) -> Unit = {},
    isIcon: Boolean = false,
    firstIcon: ImageVector = Icons.Filled.ArrowDropDown,
    secondIcon: ImageVector = Icons.Filled.ArrowDropUp,
    onIconClick: () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current


    var text by remember { mutableStateOf(TextFieldValue("")) }
    var changeIcon by rememberSaveable { mutableStateOf(true) }
    val colors = MaterialTheme.colorScheme
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val borderWidth by animateDpAsState(
        targetValue = if (isFocused) 2.dp else 1.dp,
        animationSpec = tween(durationMillis = 300)
    )
    val borderColor by animateColorAsState(
        targetValue = if (isFocused) colors.primary else colors.outline.copy(alpha = 0.5f),
        animationSpec = tween(durationMillis = 300)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = txt,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 8.dp),
            color = colors.onBackground,

            )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .border(
                    width = borderWidth,
                    color = borderColor,
                    shape = RoundedCornerShape(12.dp)
                ),
            value = text,
            placeholder = {
                Text(
                    text = txt,
                    fontSize = 14.sp
                )

            },
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus(force = true)
                    keyboardController?.hide()
                }
            ),
            onValueChange = { it ->
                text = it
                onValueChange(it.text)
            },

            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colors.surface,
                unfocusedContainerColor = colors.surface,
                cursorColor = colors.primary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = colors.onBackground,
                unfocusedTextColor = colors.onBackground
            ),
            interactionSource = interactionSource,

            trailingIcon = {
                if (isIcon) {
                    IconButton(onClick = {
                        changeIcon = !changeIcon
                        onIconClick()
                    }) {
                        Icon(
                            imageVector = if (changeIcon) firstIcon else secondIcon,
                            contentDescription = "nill"
                        )
                    }
                }

            }
        )
    }
}