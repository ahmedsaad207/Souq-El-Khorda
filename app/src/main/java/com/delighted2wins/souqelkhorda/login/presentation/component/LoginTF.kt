package com.delighted2wins.souqelkhorda.login.presentation.component


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.delighted2wins.souqelkhorda.R

@Composable
fun LoginPasswordTF(
    txt: String = stringResource(R.string.password),
    onValueChange: (String) -> Unit = {},
    horizentalPading : Int = 16
) {
    val textFieldColor = Color(0xFFF2F2F2)
    val primaryColor = Color(0xFF179C92)
    var text by remember { mutableStateOf(TextFieldValue("")) }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }

    val colors = MaterialTheme.colorScheme
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
                .padding(bottom = 8.dp),
            value = text,
            visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
            placeholder = {
                Text(
                    text = txt,
                    fontSize = 14.sp
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            onValueChange = { it ->
                text = it
                onValueChange(it.text)
            },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = primaryColor,
                disabledBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent,
                cursorColor = Color.Black,
                focusedContainerColor = Color(0xFFF5F5F5),
                unfocusedContainerColor = textFieldColor,
                disabledContainerColor = Color(0xFFF5F5F5),
                errorContainerColor = Color(0xFFF5F5F5),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
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


@Preview(showBackground = true)
@Composable
fun LoginTF(
    txt: String = stringResource(R.string.email),
    onValueChange: (String) -> Unit = {},
    isIcon: Boolean = false,
    firstIcon: ImageVector = Icons.Filled.ArrowDropDown,
    secondIcon: ImageVector = Icons.Filled.ArrowDropUp,
    onIconClick: () -> Unit = {}
) {
    val textFieldColor = Color(0xFFF2F2F2)
    val primaryColor = Color(0xFF179C92)

    var text by remember { mutableStateOf(TextFieldValue("")) }
    var changeIcon by rememberSaveable { mutableStateOf(true) }
    val colors = MaterialTheme.colorScheme

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
                .padding(bottom = 8.dp),
            value = text,
            placeholder = {
                Text(
                    text = txt,
                    fontSize = 14.sp
                )

            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            onValueChange = { it ->
                text = it
                onValueChange(it.text)
            },

            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = primaryColor,
                disabledBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent,
                cursorColor = Color.Black,
                focusedContainerColor = Color(0xFFF5F5F5),
                unfocusedContainerColor = textFieldColor,
                disabledContainerColor = Color(0xFFF5F5F5),
                errorContainerColor = Color(0xFFF5F5F5),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),

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