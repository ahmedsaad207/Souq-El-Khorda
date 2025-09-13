package com.delighted2wins.souqelkhorda.features.market.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.delighted2wins.souqelkhorda.features.market.data.ScrapStatus
import com.delighted2wins.souqelkhorda.features.market.data.User

@Composable
fun ScrapUserSection(
    userData : User,
    status: ScrapStatus,
    isRtl: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = userData.imageUrl,
                contentDescription = "User profile image",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(text = userData.name, style = MaterialTheme.typography.bodyMedium)
                Text(text = userData.location, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
        }

        Surface(
            color = status.color,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(
                text = if (isRtl) status.labelAr else status.labelEn,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ScrapUserSectionPreview() {
    val user = User(
        id = 2,
        name = "فاطمة أحمد",
        location = "الجيزة - الدقي",
        imageUrl = "https://avatar.iran.liara.run/public/boy?username=Scott"
    )
    ScrapUserSection(user,ScrapStatus.Waiting, isRtl = false)
}
