package com.delighted2wins.souqelkhorda.features.buyers.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddLocation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.delighted2wins.souqelkhorda.R

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun LocationComponent(
    onValueChange: (String) -> Unit = {},
) {

    val colors = MaterialTheme.colorScheme
    val screenWidth = LocalConfiguration.current.screenWidthDp
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.enter_your_location),
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 8.dp),
            color = colors.onBackground,

            )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                stringResource(R.string.location_is) +"\n30.0021 ,232.02565",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .height(56.dp)
                    .width((screenWidth * 0.5).dp)
                    .border(
                        1.dp, Color.Gray,
                        RoundedCornerShape(8.dp)
                    )
                    .padding(6.dp),
                color = colors.onSurface
            )
            Spacer(Modifier.width((screenWidth*0.01).dp))
            CustomCartBtn(
                onClick = {},
                imageVictor = Icons.Outlined.AddLocation,
                msg = stringResource(R.string.pick),
                btnWidth =screenWidth*0.3,
                color = colors.secondaryContainer,
                fontSize = 14.0,
                textColor =  Color.White
            )
        }
    }
}