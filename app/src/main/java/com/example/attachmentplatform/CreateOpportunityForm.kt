@file:OptIn(ExperimentalMaterial3Api::class)
package com.attachmentplatform.ui.screens.opportunities

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.attachmentplatform.viewmodel.OpportunityViewModel
import com.attachmentplatform.viewmodel.UiState
import java.text.SimpleDateFormat
import java.util.*

fun openDatePickerDialog(context: Context, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    DatePickerDialog(
        context, { _, year, month, dayOfMonth ->
            val date = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
            onDateSelected(date)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).apply {
        datePicker.minDate = System.currentTimeMillis()
    }.show()
}

@Composable
fun CreateOpportunityForm(viewModel: OpportunityViewModel) {
    var companyName by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var requirements by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("Select Duration") }
    var slotsText by remember { mutableStateOf("1") }
    var availableSlots by remember { mutableStateOf(1) }
    var applicationDeadline by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val durationOptions = listOf("Jan-Mar", "May-Jul", "Aug-Oct")
    val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    val creationState by viewModel.creationState.collectAsState()
    LaunchedEffect(creationState) {
        when (creationState) {
            is UiState.Success -> {
                errorMessage = "Opportunity posted successfully!"
                companyName = ""
                title = ""
                description = ""
                requirements = ""
                location = ""
                duration = "Select Duration"
                slotsText = "1"
                availableSlots = 1
                applicationDeadline = ""
            }
            is UiState.Error -> errorMessage = (creationState as UiState.Error).message
            else -> {}
        }
    }

    fun isValidForm(): Boolean {
        return when {
            companyName.isBlank() || title.isBlank() || description.isBlank() ||
                    location.isBlank() || duration == "Select Duration" ||
                    slotsText.isBlank() || applicationDeadline.isBlank() -> {
                errorMessage = "All fields are required."
                false
            }
            availableSlots < 1 -> {
                errorMessage = "Slots must be at least 1."
                false
            }
            !companyName.matches("^[a-zA-Z ]+$".toRegex()) -> {
                errorMessage = "Company name should only contain letters."
                false
            }
            !title.matches("^[\\w\\s.,'\"!?()-]+$".toRegex()) -> {
                errorMessage = "Title contains invalid characters."
                false
            }
            !description.matches("^[\\w\\s.,'\"!?()-]+$".toRegex()) -> {
                errorMessage = "Description contains invalid characters."
                false
            }

                !location.matches("^[a-zA-Z ]+$".toRegex()) -> {
                errorMessage = "Location should only contain letters."
                false
            }
            applicationDeadline <= currentDate -> {
                errorMessage = "Application deadline must be in the future."
                false
            }
            else -> true
        }
    }

    fun handleFormSubmit() {
        if (isValidForm()) {
            viewModel.createOpportunity(
                companyName = companyName,
                title = title,
                description = description,
                requirements = requirements,
                location = location,
                duration = duration,
                slots = availableSlots,
                deadline = applicationDeadline
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Post a New Opportunity for a Student",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
        )

        OutlinedTextField(
            value = companyName,
            onValueChange = { companyName = it },
            label = { Text("Company Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3
        )

        OutlinedTextField(
            value = requirements,
            onValueChange = { requirements = it },
            label = { Text("Requirements") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3
        )

        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Location") },
            modifier = Modifier.fillMaxWidth()
        )

        ExposedDropdownMenuBox(
            expanded = isDropdownExpanded,
            onExpandedChange = { isDropdownExpanded = !isDropdownExpanded }
        ) {
            OutlinedTextField(
                value = duration,
                onValueChange = {},
                readOnly = true,
                label = { Text("Duration") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = isDropdownExpanded,
                onDismissRequest = { isDropdownExpanded = false }
            ) {
                durationOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            duration = option
                            isDropdownExpanded = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = slotsText,
            onValueChange = {
                slotsText = it
                availableSlots = it.toIntOrNull() ?: 0
            },
            label = { Text("Available Slots") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = applicationDeadline,
            onValueChange = {},
            label = { Text("Application Deadline") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = {
                    openDatePickerDialog(context) {
                        applicationDeadline = it
                    }
                }) {
                    Icon(Icons.Filled.CalendarToday, contentDescription = null)
                }
            }
        )

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = if (creationState is UiState.Success) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.error
            )
        }

        Button(
            onClick = { handleFormSubmit() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Post Opportunity")
        }
    }
}
