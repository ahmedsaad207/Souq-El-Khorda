package com.delighted2wins.souqelkhorda.features.sign_up.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.core.components.OneIconCard
import com.delighted2wins.souqelkhorda.features.sign_up.presentation.component.CustomDropdownMenu
import com.delighted2wins.souqelkhorda.features.sign_up.presentation.component.CustomTextField
import com.delighted2wins.souqelkhorda.features.login.presentation.component.LoginPasswordTF

@Composable
fun SignUpScreen(
//    viewModel: SignUpViewModel = koinViewModel(),
    onBackClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
    snackBarHostState: SnackbarHostState
) {
    val primaryColor = Color(0xFF179C92)


//    val colors = MaterialTheme.colorScheme

//    val governorates by viewModel.governorates.collectAsStateWithLifecycle()
//    val areas by viewModel.areas.collectAsStateWithLifecycle()

//    val registerState by viewModel.registerState.collectAsStateWithLifecycle()
    val governorates = listOf("Cairo", "Giza", "Alexandria", "Luxor", "Aswan")

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var governorate by remember { mutableStateOf("") }
    var addressDetails by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var governorateExpanded by remember { mutableStateOf(false) }

//    LaunchedEffect(Unit) {
//        viewModel.message.collect { message ->
//            snackBarHostState.showSnackbar(message)
//        }
//    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
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
                    password = it
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
                    governorate = it
//                    viewModel.getAreas(governorates.find { it.name == governorate }?.id ?: 0)
                },
                label = stringResource(R.string.governorate),
                options = governorates,
                expanded = governorateExpanded,
                onExpandedChange = { governorateExpanded = it }
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
                    onRegisterClick()
//                    viewModel.signUp(
//                        username = username,
//                        pharmacyName = pharmacyName,
//                        email = email,
//                        phone = phone,
//                        governorate = governorate,
//                        areaId = areas.find { it.name == area }?.id ?: 0,
//                        addressDetails = addressDetails,
//                        invitationCode = invitationCode,
//                        password = password,
//                        confirmPassword = confirmPassword
//                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryColor,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = stringResource(R.string.register),
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

        }

//        when(registerState){
//            is Response.Error -> {
//                // NoInternet()
//            }
//            is Response.Loading -> {
//                LoadingView()
//            }
//            is Response.Success -> {
//                onRegisterClick()
//            }
//            null -> {
//            }
//        }
    }
}



