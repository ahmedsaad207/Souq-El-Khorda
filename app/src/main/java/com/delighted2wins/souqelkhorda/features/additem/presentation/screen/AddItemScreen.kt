package com.delighted2wins.souqelkhorda.features.additem.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.delighted2wins.souqelkhorda.features.additem.presentation.AddItemIntent
import com.delighted2wins.souqelkhorda.features.additem.presentation.viewmodel.AddItemViewModel
import com.delighted2wins.souqelkhorda.core.model.Scrap

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemScreen(
    category: String,
    viewModel: AddItemViewModel = hiltViewModel(),
    onBack: () -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Add $category Item") },
                navigationIcon = {
                    IconButton(
                        onClick = { onBack() }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->

        Column {

            Text(
                text = "Here you can add details for $category",
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp),
                style = MaterialTheme.typography.bodyLarge
            )

            Button(
                onClick = {
                    val dummyScrap = Scrap(
                        category = category,
                        unit = "Kg",
                        amount = 5.0,
                        description = "Scrap item example"
                    )
                    viewModel.processIntent(AddItemIntent.AddIntent(dummyScrap))
                },
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text("Add item")
            }

            if (state.isSaved) {
                LaunchedEffect(Unit) {
                    onBack()
                }
            }

            if (state.error != null) {
                Text(
                    text = "Error: ${state.error}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}