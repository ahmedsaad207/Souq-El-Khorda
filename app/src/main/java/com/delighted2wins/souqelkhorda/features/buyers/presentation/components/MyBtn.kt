package com.delighted2wins.souqelkhorda.features.buyers.presentation.components


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.features.authentication.presentation.component.DotLoadingIndicator


@Composable
fun CustomCartBtn(
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    imageVictor: ImageVector = Icons.Filled.ShoppingCart,
    msg: String = stringResource(
        R.string.address_details
    ),
    color: Color = Color.White,
    textColor: Color = MaterialTheme.colorScheme.surface,
    btnWidth: Double = 0.0,
    fontSize: Double = 16.0
) {
    Button(
        onClick = {
            onClick()
        },
        modifier = Modifier
            .width(btnWidth.dp)
            .padding(bottom = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = MaterialTheme.colorScheme.surface
        ),
        enabled = enabled

    ) {
        if (!enabled) {
            DotLoadingIndicator()
        } else {
            Text(
                text = msg,
                color = textColor,
                fontSize = fontSize.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Icon(imageVector = imageVictor, contentDescription = "", tint = textColor)
        }
    }
}