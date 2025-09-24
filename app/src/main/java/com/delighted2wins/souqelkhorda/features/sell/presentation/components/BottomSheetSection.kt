package com.delighted2wins.souqelkhorda.features.sell.presentation.components

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.delighted2wins.souqelkhorda.core.components.CustomButton
import com.delighted2wins.souqelkhorda.core.components.CustomCard
import com.delighted2wins.souqelkhorda.core.enums.BottomSheetMode
import com.delighted2wins.souqelkhorda.core.enums.MeasurementType
import com.delighted2wins.souqelkhorda.core.enums.ScrapType
import com.delighted2wins.souqelkhorda.core.extensions.dashedBorder
import com.delighted2wins.souqelkhorda.core.model.Scrap

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun BottomSheetSection(
    mode: BottomSheetMode,
    scrap: Scrap? = null,
    onCancelClick: () -> Unit,
    onAddScrapClick: (Scrap) -> Unit,
    onUpdateScrapClick: (Scrap) -> Unit,
) {

    val category = remember { mutableStateOf("") }
    val amount = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val selectedScrapType = remember { mutableStateOf(ScrapType.Aluminum) }
    val selectedMeasurementType = remember { mutableStateOf(MeasurementType.Weight) }

    var selectedImages by remember {
        mutableStateOf<List<Uri>>(emptyList())
    }

    LaunchedEffect(mode, scrap) {
        if (mode == BottomSheetMode.EDIT && scrap != null) {

            val type = ScrapType.entries.find { it.name == scrap.category }
            if (type != null) {
                selectedScrapType.value = type
            } else {
                category.value = scrap.category
                selectedScrapType.value = ScrapType.CustomScrap
            }

            MeasurementType.entries.find { it.name == scrap.unit }?.let {
                selectedMeasurementType.value = it
            }

            amount.value = scrap.amount
            description.value = scrap.description
            selectedImages = scrap.images?.map { it.toUri() }
                ?: emptyList()
        }
    }

    val keyboardType = when (selectedMeasurementType.value) {
        MeasurementType.Weight -> KeyboardType.Decimal
        MeasurementType.Pieces -> KeyboardType.Number
    }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            if (uri != null) {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                if (!selectedImages.contains(uri)) {
                    selectedImages = selectedImages + uri
                }
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(0.3f),
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AddBox,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = if (mode == BottomSheetMode.ADD) "Add New Scrap" else "Edit Scrap",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "Add to your recycling order",
                    style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.primary)
                )
            }
            IconButton(
                onClick = onCancelClick,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(color = Color.LightGray.copy(0.2f))
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.Gray
                )

            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {

            item {
                CustomCard(
                    title = "Scrap type",
                    color = Color.Blue
                ) {
                    CustomDropDown(
                        modifier = Modifier.fillMaxWidth(),
                        options = ScrapType.entries.toTypedArray(),
                        selectedOption = selectedScrapType.value,
                        onOptionSelected = { selectedScrapType.value = it },
                        labelMapper = { it.label }
                    )
                }
            }

            item {
                if (selectedScrapType.value == ScrapType.CustomScrap) {
                    CustomCard(
                        title = "Custom scrap name ",
                        color = Color.Blue
                    ) {
                        CustomTextField(
                            textFieldModifier = Modifier
                                .fillMaxWidth(),
                            state = category,
                            onValueChange = { category.value = it },
                            placeholder = "e.g., Copper wire"
                        )
                    }
                }
            }

            item {
                CustomCard(
                    title = "Quantity & Measurement ",
                    color = Color.Magenta
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                        ) {
                            CustomTextFieldLabel("Measurement Type")
                            CustomDropDown(
                                options = MeasurementType.entries.toTypedArray(),
                                selectedOption = selectedMeasurementType.value,
                                onOptionSelected = { selectedMeasurementType.value = it },
                                labelMapper = { it.label }
                            )
                        }

                        CustomTextField(
                            modifier = Modifier.weight(1f),
                            state = amount,
                            label = "Quantity",
                            onValueChange = { newValue ->
                                when (selectedMeasurementType.value) {
                                    MeasurementType.Weight -> {
                                        // allow only decimals
                                        if (newValue.isEmpty() || newValue.matches(Regex("^\\d*\\.?\\d*$"))) {
                                            amount.value = newValue
                                        }
                                    }

                                    MeasurementType.Pieces -> {
                                        // allow only integers
                                        if (newValue.isEmpty() || newValue.matches(Regex("^\\d*$"))) {
                                            amount.value = newValue
                                        }
                                    }
                                }
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                            placeholder = "eg, 10"
                        )
                    }
                }
            }

            item {
                CustomCard(
                    title = "Description ",
                    color = Color.Blue,
                    subTitle = "(Optional)"
                ) {
                    CustomTextField(
                        textFieldModifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp),
                        state = description,
                        onValueChange = { description.value = it },
                        placeholder = "Details about the item"
                    )
                }
            }

            item {
                CustomCard(
                    title = "Photos ",
                    color = Color(0xFFFF1493),
                    subTitle = "(Up to 5 images)",
                    icon = Icons.Outlined.CameraAlt,
                    iconTint = Color(0xFFFF69B4)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CustomTextFieldLabel(label = "Photos")
                        Text(
                            text = "${selectedImages.size}/5"
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    // images
                    BoxWithConstraints(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val itemSize = (maxWidth - 32.dp) / 5
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            items(
                                items = selectedImages,
                                key = { it }
                            ) { uri ->
                                Box(
                                    modifier = Modifier
                                        .size(itemSize)
                                        .clip(RoundedCornerShape(8.dp))
                                        .animateItem()
                                ) {
                                    AsyncImage(
                                        model = uri,
                                        contentDescription = "Selected Image",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )

                                    IconButton(
                                        onClick = {
                                            selectedImages = selectedImages - uri
                                        },
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .background(
                                                color = Color.Black.copy(0.5f),
                                                shape = CircleShape
                                            )
                                            .size(24.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Remove Image",
                                            tint = Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    // get image from user
                    if (selectedImages.size < 5) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .dashedBorder(
                                    color = Color.Gray,
                                    strokeWidth = 2.dp,
                                    cornerRadius = 12.dp,
                                    dashLength = 20f,
                                    gapLength = 6f,
                                    alpha = 0.5f
                                ),
                            contentAlignment = Alignment.Center

                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.CameraAlt,
                                    contentDescription = "Camera",
                                    tint = Color.Black.copy(0.5f),
                                    modifier = Modifier.size(48.dp)
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = "Tap to add image",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Spacer(Modifier.height(4.dp))
                                OutlinedButton(
                                    onClick = {
                                        launcher.launch(arrayOf("image/*"))
                                    },
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Add",
                                        tint = Color.Black
                                    )
                                    Text(
                                        text = "Add Photos",
                                        color = Color.Black
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // cancel and add scrap buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CustomButton(
                onClick = onCancelClick,
                modifier = Modifier.weight(1f),
                text = "Cancel",
                textColor = Color.Black,
                containerColor = Color.White
            )

            CustomButton(
                onClick = {
                    val newScrap = Scrap(
                        category = if (selectedScrapType.value == ScrapType.CustomScrap) category.value else selectedScrapType.value.name,
                        unit = selectedMeasurementType.value.name,
                        amount = amount.value,
                        description = description.value,
                        images = selectedImages.map { it.toString() }
                    )


                    if (mode == BottomSheetMode.ADD) {
                        onAddScrapClick(newScrap)
                    } else {
                        onUpdateScrapClick(newScrap.copy(id = scrap?.id ?: 1))
                    }
                },
                modifier = Modifier.weight(1f),
                text = if (mode == BottomSheetMode.EDIT) "Update Scrap" else "Add Scrap",
            )
        }

    }
}

