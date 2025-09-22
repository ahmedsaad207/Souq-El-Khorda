package com.delighted2wins.souqelkhorda.features.sell.presentation.components

import android.R.attr.contentDescription
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.core.components.CustomCard
import com.delighted2wins.souqelkhorda.core.enums.MeasurementType
import com.delighted2wins.souqelkhorda.core.enums.ScrapType
import com.delighted2wins.souqelkhorda.core.model.Scrap

@Composable
fun AddScrapSection(
    onCancelClick: () -> Unit,
    onAddClick: (Scrap) -> Unit
) {

    var category by remember { mutableStateOf("") }
    val amount = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val selectedScrapType = remember { mutableStateOf(ScrapType.Aluminum) }
    val selectedMeasurementType = remember { mutableStateOf(MeasurementType.Weight) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Default.Image,
                contentDescription = ""
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text("Add New Scrap")
                Text("Add to your recycling order")
            }
            IconButton(
                onClick = onCancelClick
            ) {
                Icon(
                    imageVector = Icons.Default.Cancel,
                    contentDescription = ""
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
                            state = description,
                            onValueChange = { category = it }
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
                        modifier = Modifier.fillMaxWidth()
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
                            }
                        )
                    }
                }
            }

            item {
                CustomCard(
                    title = "Description ",
                    color = Color.Blue
                ) {
                    CustomTextField(
                        textFieldModifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp),
                        state = description,
                        onValueChange = { description.value = it }
                    )
                }
            }

            item {
                CustomCard(
                    title = "Photos ",
                    color = Color.Red
                ) { }
            }
        }

        // cancel and add scrap buttons
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = onCancelClick,
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancel")
            }

            Button(
                onClick = {
                    val scrap = Scrap(
                        category = if (selectedScrapType.value == ScrapType.CustomScrap) category else selectedScrapType.value.label,
                        unit = selectedMeasurementType.value.label,
                        amount = amount.value,
                        description = description.value,
                    )
                    Log.d(
                        "TAG",
                        "AddScrapSection: category: ${scrap.category}, unit: ${scrap.unit}, amount: ${scrap.amount}, description: ${scrap.description}"
                    )
                    onAddClick(scrap)
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Add Scrap")
            }
        }

    }


}