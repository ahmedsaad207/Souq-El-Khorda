import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextFieldWithIcon(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    onIconClick: () -> Unit,
    imageVectorIcon: ImageVector? = null,
    painterIcon: Painter? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    cornerRadius: Dp = 12.dp
) {
    val colors = MaterialTheme.colorScheme
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val borderWidth by animateDpAsState(targetValue = if (isFocused) 2.dp else 1.dp, animationSpec = tween(300))
    val borderColor by animateColorAsState(
        targetValue = if (isFocused) colors.primary else colors.outline.copy(alpha = 0.5f),
        animationSpec = tween(300)
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(color = colors.onBackground),
            modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            readOnly = readOnly,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(cornerRadius))
                .border(width = borderWidth, color = borderColor, shape = RoundedCornerShape(cornerRadius)),
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodySmall.copy(color = colors.onSurfaceVariant)
                )
            },
            trailingIcon = {
                if (trailingContent != null) {
                    trailingContent()
                } else {
                    if (imageVectorIcon != null) {
                        Icon(
                            imageVector = imageVectorIcon,
                            contentDescription = null,
                            tint = if (!readOnly) colors.primary else colors.onSurfaceVariant,
                            modifier = Modifier
                                .size(18.dp)
                                .clickable { onIconClick() }
                        )
                    } else if (painterIcon != null) {
                        Icon(
                            painter = painterIcon,
                            contentDescription = null,
                            tint = if (!readOnly) colors.primary else colors.onSurfaceVariant,
                            modifier = Modifier
                                .size(18.dp)
                                .clickable { onIconClick() }
                        )
                    }
                }
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colors.surface,
                unfocusedContainerColor = colors.surface,
                cursorColor = colors.primary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = colors.onBackground,
                unfocusedTextColor = colors.onBackground
            ),
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = colors.onBackground),
            singleLine = singleLine,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = visualTransformation,
            interactionSource = interactionSource
        )
    }
}
