package com.delighted2wins.souqelkhorda.features.buyers.presentation.components

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddLocation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.core.utils.GPSLocation
import com.delighted2wins.souqelkhorda.core.utils.requestGps
import com.delighted2wins.souqelkhorda.features.buyers.presentation.view_model.LocationViewModel

@SuppressLint("MissingPermission", "ConfigurationScreenWidthHeight")
@Composable
fun LocationComponent(
    viewModel: LocationViewModel = hiltViewModel(),
    onValueChange: (Double, Double) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val activity = context as? Activity ?: return
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val colors = MaterialTheme.colorScheme

    val gpsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) {
        if (GPSLocation.isLocationEnabled(activity)) {
            viewModel.updateGpsState(true)
            viewModel.fetchLocation(onValueChange)
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (granted) {
            if (uiState.isGpsEnabled) {
                viewModel.fetchLocation(onValueChange)
            } else {
                requestGps(activity, gpsLauncher, viewModel, onValueChange)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.enter_your_location),
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 8.dp),
            color = colors.onBackground
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width((screenWidth * 0.5).dp)
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                    .padding(6.dp),
                contentAlignment = Alignment.Center
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                } else {
                    Text(
                        stringResource(R.string.location_is) + "\n${uiState.locationText}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = colors.onSurface
                    )
                }
            }

            Spacer(Modifier.width((screenWidth * 0.01).dp))

            CustomCartBtn(
                onClick = {
                    if (GPSLocation.checkPermission(context)) {
                        if (uiState.isGpsEnabled) {
                            viewModel.fetchLocation(onValueChange)
                        } else {
                            requestGps(activity, gpsLauncher, viewModel, onValueChange)
                        }
                    } else {
                        permissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        )
                    }
                },
                imageVictor = Icons.Outlined.AddLocation,
                msg = when {
                    uiState.isLoading -> stringResource(R.string.load)
                    uiState.isGpsEnabled -> stringResource(R.string.pick)
                    else -> "GPS"
                },
                btnWidth = screenWidth * 0.3,
                color = colors.secondaryContainer,
                fontSize = 14.0,
                textColor = Color.White
            )
        }
    }
}
