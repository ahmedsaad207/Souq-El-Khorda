package com.delighted2wins.souqelkhorda.core.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    contentDescription: String? = null,
    iconTint: Color = Color.White,
    iconSize: Int = 20,
    text: String,
    textColor: Color = Color.White,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    isLoading: MutableState<Boolean> = mutableStateOf(false),
    textLoading: String = "",
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isLoading.value) containerColor.copy(alpha = 0.7f) else containerColor,
            disabledContainerColor = containerColor.copy(alpha = 0.7f),
            contentColor = textColor,
            disabledContentColor = textColor.copy(alpha = 0.7f)
        ),
        modifier = modifier
            .height(56.dp),
        shape = RoundedCornerShape(12.dp)
    ) {

        if (isLoading.value) {
            CircularProgressIndicator(
                color = Color.White,
                strokeWidth = 2.dp,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))

        } else if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = iconTint,
                modifier = Modifier.size(iconSize.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Text(
            text = if (isLoading.value) textLoading else text,
            color = textColor,
            style = textStyle.copy(fontWeight = FontWeight.SemiBold)
        )
    }
}