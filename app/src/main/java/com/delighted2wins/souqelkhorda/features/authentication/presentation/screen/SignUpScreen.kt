package com.delighted2wins.souqelkhorda.features.authentication.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.core.components.OneIconCard
import com.delighted2wins.souqelkhorda.core.enums.GovernorateEnum
import com.delighted2wins.souqelkhorda.features.authentication.data.model.SignUpRequestDto
import com.delighted2wins.souqelkhorda.features.authentication.presentation.component.CustomDropdownMenu
import com.delighted2wins.souqelkhorda.features.authentication.presentation.component.CustomTextField
import com.delighted2wins.souqelkhorda.features.authentication.presentation.state.AuthenticationState
import com.delighted2wins.souqelkhorda.features.authentication.presentation.viewmodel.SignUpViewModel
import com.delighted2wins.souqelkhorda.features.login.presentation.component.LoginPasswordTF


@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
    snackBarHostState: SnackbarHostState,
    innerPadding: PaddingValues
) {
    val primaryColor = Color(0xFF179C92)

    val registerState by viewModel.registerState.collectAsStateWithLifecycle()
    val isLoading = registerState is AuthenticationState.Loading
    val governorates = GovernorateEnum.getAllGovernorate()

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var governorate by remember { mutableStateOf("") }
    var addressDetails by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var area by remember { mutableStateOf("") }

    var governorateExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.message.collect { message ->
            snackBarHostState.showSnackbar(message)
        }
    }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            OneIconCard(
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                headerTxt = stringResource(R.string.register_new_account),
                onClick = {
                    onBackClick()
                },
                titleSize = 14
            )
            CustomTextField(
                value = username,
                onValueChange = { username = it },
                label = stringResource(R.string.username),
                placeholder = stringResource(R.string.enter_your_username)
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = email,
                onValueChange = { email = it },
                label = stringResource(R.string.email_signup),
                placeholder = stringResource(R.string.enter_your_email),
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(16.dp))

            LoginPasswordTF(
                onValueChange = {
                    password = it
                },
                horizentalPading = 0
            )

            Spacer(modifier = Modifier.height(16.dp))

            LoginPasswordTF(
                txt = stringResource(R.string.confirm_password),
                onValueChange = {
                    confirmPassword = it
                },
                horizentalPading = 0
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = phone,
                onValueChange = { phone = it },
                label = stringResource(R.string.phone),
                placeholder = stringResource(R.string.enter_phone_number),
                keyboardType = KeyboardType.Phone
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomDropdownMenu(
                value = governorate,
                onValueChange = {
                    governorate = GovernorateEnum.getValue(it)
                },
                label = stringResource(R.string.governorate),
                options = governorates,
                expanded = governorateExpanded,
                onExpandedChange = { governorateExpanded = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = area,
                onValueChange = { area = it },
                label = stringResource(R.string.area),
                placeholder = stringResource(R.string.enter_your_area),
            )

            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                value = addressDetails,
                onValueChange = { addressDetails = it },
                label = stringResource(R.string.address_details),
                placeholder = stringResource(R.string.enter_full_address_details),
                singleLine = false
            )


            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    viewModel.signUp(
                        SignUpRequestDto(
                            name = username,
                            email = email,
                            phone = phone,
                            password = password,
                            passwordConfirmation = confirmPassword,
                            governorate = governorate,
                            address = addressDetails,
                            area = area
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryColor,
                    contentColor = Color.White
                ),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Text(text = stringResource(R.string.register), fontSize = 16.sp)
                }
            }
            Spacer(modifier = Modifier
                .height(48.dp))

        }

        when (registerState) {
            is AuthenticationState.Error -> {
            }
            is AuthenticationState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = primaryColor

                    )

                }
            }
            is AuthenticationState.Idle -> {
            }
            is AuthenticationState.Success -> {
                onRegisterClick()
            }
        }

}



