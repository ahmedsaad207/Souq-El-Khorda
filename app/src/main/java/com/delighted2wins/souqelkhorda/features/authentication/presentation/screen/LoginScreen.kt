package com.delighted2wins.souqelkhorda.features.authentication.presentation.screen


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.features.authentication.presentation.component.DotLoadingIndicator
import com.delighted2wins.souqelkhorda.features.authentication.presentation.contract.AuthenticationIntent
import com.delighted2wins.souqelkhorda.features.authentication.presentation.contract.AuthenticationState
import com.delighted2wins.souqelkhorda.features.authentication.presentation.viewmodel.LoginViewModel
import com.delighted2wins.souqelkhorda.features.login.presentation.component.LoginPasswordTF
import com.delighted2wins.souqelkhorda.features.login.presentation.component.LoginTF


@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun LoginScreen(
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
    snackBarHostState: SnackbarHostState,
    viewModel: LoginViewModel = hiltViewModel(),
    innerPadding: PaddingValues
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val colors = MaterialTheme.colorScheme
    val loginState by viewModel.loginState.collectAsStateWithLifecycle()
    val isLoading = loginState is AuthenticationState.Loading
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current


    LaunchedEffect(Unit) {
        viewModel.message.collect { message ->
            snackBarHostState.showSnackbar(message)
        }
    }

    LaunchedEffect(loginState) {
        when (loginState) {
            is AuthenticationState.Success -> {
                onLoginClick()
            }
            is AuthenticationState.Error -> {
            }
            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(colors.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            Modifier.height((screenHeight * 0.06).dp)
        )

        Image(
            painter = painterResource(R.drawable.logo),
            modifier = Modifier
                .size((screenWidth * 0.25).dp)
                .clip(CircleShape),
            contentScale = ContentScale.FillBounds,
            contentDescription = "App Logo",
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            stringResource(R.string.welcome_back), fontSize = 20.sp,
            color = colors.primary,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            stringResource(R.string.to_continue_login_now),
            fontSize = 12.sp,
        )

        Spacer(modifier = Modifier.height(32.dp))


        LoginTF(onValueChange = {
            email = it.trim()
        })
        Spacer(
            Modifier.height((screenHeight * 0.01).dp)
        )
        LoginPasswordTF(onValueChange = {
            password = it.trim()
        })
        Spacer(
            Modifier.height((screenHeight * 0.01).dp)
        )
        Button(
            shape = RoundedCornerShape(8.dp),
            onClick = {
                focusManager.clearFocus(force = true)
                keyboardController?.hide()
                viewModel.processIntent(AuthenticationIntent.Login(email,password))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF179C92),
                contentColor = Color.White
            ),
            enabled = !isLoading
        ) {
            if (isLoading) {
                DotLoadingIndicator()
            } else {
                Text(
                    text = stringResource(R.string.login),
                    fontSize = 14.sp
                )
            }
        }
        Spacer(
            Modifier.height((screenHeight * 0.01).dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.weight(1f))
            Text(
                text = stringResource(R.string.don_t_have_an_account_yet),
                fontSize = 12.sp,
            )
            TextButton(
                onClick = {
                    focusManager.clearFocus(force = true)
                    keyboardController?.hide()
                    onRegisterClick()
                }
            ) {
                Text(
                    text = stringResource(R.string.register_here),
                    fontSize = 12.sp,
                    color = Color(0xFF179C92),
                )
            }
            Spacer(Modifier.width(4.dp))
        }
    }
}


