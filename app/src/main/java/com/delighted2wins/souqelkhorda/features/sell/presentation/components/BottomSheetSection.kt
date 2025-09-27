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
import androidx.compose.material.icons.filled.CheckCircle
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.core.components.CustomButton
import com.delighted2wins.souqelkhorda.core.components.CustomCard
import com.delighted2wins.souqelkhorda.core.components.CustomDropDown
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
            selectedImages = scrap.images.map { it.toUri() }
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
                    text = if (mode == BottomSheetMode.ADD) stringResource(R.string.add_new_scrap) else stringResource(
                        R.string.edit_scrap
                    ),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    stringResource(R.string.add_to_your_recycling_order),
                    style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                )
            }
            IconButton(
                onClick = onCancelClick,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.close),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )

            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {

            item {
                CustomCard(
                    title = stringResource(R.string.scrap_type),
                    color = Color.Blue
                ) {
                    CustomDropDown(
                        modifier = Modifier.fillMaxWidth(),
                        options = ScrapType.entries.toTypedArray(),
                        selectedOption = selectedScrapType.value,
                        onOptionSelected = { selectedScrapType.value = it },
                        labelMapper = { it.getLabel(context) },
                        iconTintMapper = {it.tint},
                        iconResMapper = { it.iconRes }
                    )
                }
            }

            item {
                if (selectedScrapType.value == ScrapType.CustomScrap) {
                    CustomCard(
                        title = stringResource(R.string.custom_scrap_name),
                        color = Color.Blue
                    ) {
                        CustomTextField(
                            textFieldModifier = Modifier
                                .fillMaxWidth(),
                            state = category,
                            onValueChange = { category.value = it },
                            placeholder = stringResource(R.string.e_g_copper_wire)
                        )
                    }
                }
            }

            item {
                CustomCard(
                    title = stringResource(R.string.quantity_measurement),
                    color = Color.Magenta
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                        ) {
                            CustomTextFieldLabel(stringResource(R.string.measurement_type))
                            CustomDropDown(
                                options = MeasurementType.entries.toTypedArray(),
                                selectedOption = selectedMeasurementType.value,
                                onOptionSelected = { selectedMeasurementType.value = it },
                                labelMapper = { it.getLabel(context) }
                            )
                        }

                        CustomTextField(
                            modifier = Modifier.weight(1f),
                            state = amount,
                            label = stringResource(R.string.quantity),
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
                            placeholder = stringResource(R.string.eg_10)
                        )
                    }
                }
            }

            item {
                CustomCard(
                    title = stringResource(R.string.description),
                    color = Color.Blue,
                    subTitle = stringResource(R.string.optional)
                ) {
                    CustomTextField(
                        textFieldModifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp),
                        state = description,
                        onValueChange = { description.value = it },
                        placeholder = stringResource(R.string.details_about_the_item)
                    )
                }
            }

            item {
                CustomCard(
                    title = stringResource(R.string.photos),
                    color = Color(0xFFFF1493),
                    subTitle = stringResource(R.string.up_to_5_images),
                    icon = Icons.Outlined.CameraAlt,
                    iconTint = Color(0xFFFF69B4)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CustomTextFieldLabel(label = stringResource(R.string.photos))
                        Text(
                            text = stringResource(R.string._5, selectedImages.size),
                            color = MaterialTheme.colorScheme.onSurface
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
                                        contentDescription = context.getString(R.string.selected_image),
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
                                                color = MaterialTheme.colorScheme.surfaceVariant.copy(0.7f),
                                                shape = CircleShape
                                            )
                                            .size(24.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = stringResource(R.string.remove_image),
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant
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
                                    color = MaterialTheme.colorScheme.outline.copy(0.5f),
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
                                    contentDescription = stringResource(R.string.camera),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(48.dp)
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = stringResource(R.string.tap_to_add_image),
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        MaterialTheme.colorScheme.onSurface
                                    )
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
                                        contentDescription = stringResource(R.string.add),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = stringResource(R.string.add_photos),
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            modifier = Modifier.size(36.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            text = stringResource(R.string.almost_there),
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                        Text(
                            text = stringResource(R.string.upload_clear_high_quality_photos_of_your_scrap_to_attract_the_best_offers),
                            style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.primary)
                                .copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                            textAlign = TextAlign.Center
                        )

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
                text = stringResource(R.string.cancel),
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
                text = if (mode == BottomSheetMode.EDIT) stringResource(R.string.update_scrap) else stringResource(
                    R.string.add_scrap
                ),
            )
        }

    }
}

