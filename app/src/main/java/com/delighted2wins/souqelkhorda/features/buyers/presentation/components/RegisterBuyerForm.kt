package com.delighted2wins.souqelkhorda.features.buyers.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Recycling
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.core.enums.AuthMsgEnum
import com.delighted2wins.souqelkhorda.core.enums.ScrapTypeEnum
import com.delighted2wins.souqelkhorda.features.authentication.presentation.component.DotLoadingIndicator
import com.delighted2wins.souqelkhorda.features.buyers.presentation.state.BuyerState
import com.delighted2wins.souqelkhorda.features.buyers.presentation.view_model.BuyerViewModel
import kotlinx.coroutines.launch


@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun RegisterBuyerForm(
    modifier: Modifier = Modifier,
    viewModel: BuyerViewModel = hiltViewModel(),
    snackBarHostState: SnackbarHostState,
    onRegisterClick: () -> Unit,
    isOnline: Boolean
) {
    val ctx = LocalContext.current
    var selected by remember { mutableStateOf(listOf<String>()) }
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val colors = MaterialTheme.colorScheme
    val valuesForApi = selected.map { ScrapTypeEnum.getValue(it) }
    var latitude by remember { mutableDoubleStateOf(0.0) }
    var longitude by remember { mutableDoubleStateOf(0.0) }
    val scope = rememberCoroutineScope ()

    val state by viewModel.registerState.collectAsStateWithLifecycle()
    val isLoading = state is BuyerState.Loading

    LaunchedEffect(Unit) {
        viewModel.message.collect { message ->
            snackBarHostState.showSnackbar(message)
        }
    }
    LaunchedEffect(state) {
        when (state) {
            is BuyerState.RegisterSuccess -> {
                onRegisterClick()
            }

            is BuyerState.Error -> {
            }

            else -> Unit
        }
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 12.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            width = 1.dp,
            color = Color.LightGray.copy(0.5f)
        ),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = colors.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Recycling,
                        contentDescription = null,
                        tint = Color.Green,
                        modifier = Modifier.size(30.dp)
                    )
                }
                Text(
                    text = stringResource(R.string.register_shop_form),
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF16A14E),
                                Color(0xFF2565E6)
                            )
                        ),
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    ),

                    textAlign = TextAlign.Center
                )
            }
            Text(
                text = stringResource(R.string.form_sub_title),
                style = TextStyle(
                    color = colors.onSurface.copy(alpha = 0.8f),
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp
                ),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        width = 1.dp,
                        color = colors.secondary.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .background(colors.tertiary)

            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colors.secondary.copy(alpha = 0.1f)),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {

                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Security,
                                contentDescription = null,
                                tint = colors.primary,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                        Text(
                            stringResource(R.string.shop_info),
                            color = colors.primary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(Modifier.height(16.dp))
                    LocationComponent { lat, lon ->
                        latitude = lat
                        longitude = lon

                    }
                    Spacer(Modifier.height(24.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Recycling,
                                contentDescription = null,
                                tint = Color.Green,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Text(
                            text = stringResource(R.string.korda_available_type),
                            style = TextStyle(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF16A14E),
                                        Color(0xFF2565E6)
                                    )
                                ),
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            ),

                            textAlign = TextAlign.Center
                        )
                    }
                    KordaTypeDrobDownMenu(
                        options = ScrapTypeEnum.getAllScrapTypes(),
                        selectedOptions = selected,
                        onSelectionChange = { selectedList ->
                            selected = selectedList
                        }

                    )
                    Spacer(Modifier.height(16.dp))
                    Button(
                        onClick = {
                            if (isOnline) {
                                viewModel.registerBuyer(
                                    latitude,
                                    longitude,
                                    valuesForApi
                                )
                            } else {
                                scope.launch {
                                    snackBarHostState.showSnackbar(AuthMsgEnum.NOINTRENET.getMsg())
                                }
                            }
                        },
                        modifier = Modifier
                            .width((screenWidth * 0.85).dp)
                            .padding(6.dp)
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFF2565E6),
                                        Color(0xFF1F7EA7),
                                        Color(0xFF00A9A2),
                                        Color(0xFF00B14F)
                                    )
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        if (isLoading) {
                            DotLoadingIndicator()
                        } else {
                            Text(
                                stringResource(R.string.register_shop),
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

            }
        }
    }
}

