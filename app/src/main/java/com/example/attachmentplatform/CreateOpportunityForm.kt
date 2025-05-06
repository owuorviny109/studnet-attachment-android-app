package com.attachmentplatform.ui.screens.opportunities

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

fun openDatePickerDialog(context: Context, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context, { _, year, month, dayOfMonth ->
            val date = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
            onDateSelected(date)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    datePickerDialog.datePicker.minDate = System.currentTimeMillis()
    datePickerDialog.show()
}

@Composable
fun CreateOpportunityForm() {
    var companyName by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var requirements by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("Select Duration") }
    var availableSlots by remember { mutableStateOf(1) }
    var applicationDeadline by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var isFormValid by remember { mutableStateOf(true) }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val durationOptions = listOf("Jan-Mar", "May-Jul", "Aug-Oct")
    val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    // Validation functions
    fun isValidCompanyName(name: String) = name.matches("^[a-zA-Z ]+$".toRegex())
    fun isValidTitle(title: String) = title.contains("IT", ignoreCase = true)
    fun isValidDescription(description: String) = description.matches("^[a-zA-Z ]+$".toRegex())
    fun isValidLocation(location: String) = location.matches("^[a-zA-Z ]+$".toRegex())
    fun isValidAvailableSlots(slots: Int) = slots > 0
    fun isValidApplicationDeadline(deadline: String) = deadline > currentDate

    fun handleFormSubmit() {
        when {
            companyName.isBlank() || title.isBlank() || description.isBlank() ||
                    location.isBlank() || duration.isBlank() || availableSlots == 0 ||
                    applicationDeadline.isBlank() -> {
                errorMessage = "All fields are required."
                isFormValid = false
            }

            !isValidCompanyName(companyName) -> {
                errorMessage = "Company name should only contain letters."
                isFormValid = false
            }

            !isValidTitle(title) -> {
                fun isValidTitle(title: String) = title.length >= 3

                isFormValid = false
            }

            !isValidDescription(description) -> {
                errorMessage = "Description should only contain letters."
                isFormValid = false
            }

            !isValidLocation(location) -> {
                errorMessage = "Location should only contain letters."
                isFormValid = false
            }

            !isValidAvailableSlots(availableSlots) -> {
                errorMessage = "Available slots should be a positive number."
                isFormValid = false
            }

            !isValidApplicationDeadline(applicationDeadline) -> {
                errorMessage = "Application deadline must be a future date."
                isFormValid = false
            }

            else -> {
                errorMessage = "Form submitted successfully!"
                isFormValid = true
            }
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
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = companyName,
            onValueChange = { companyName = it },
            label = { Text("Company Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
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
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = duration,
            onValueChange = { duration = it },
            label = { Text("Duration") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = { isDropdownExpanded = !isDropdownExpanded }) {
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = "Dropdown Icon")
                }
            }
        )

        DropdownMenu(
            expanded = isDropdownExpanded,
            onDismissRequest = { isDropdownExpanded = false },
            modifier = Modifier.fillMaxWidth()
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

        OutlinedTextField(
            value = availableSlots.toString(),
            onValueChange = {
                availableSlots = it.toIntOrNull() ?: 1
            },
            label = { Text("Available Slots") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        OutlinedTextField(
            value = applicationDeadline,
            onValueChange = { applicationDeadline = it },
            label = { Text("Application Deadline") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = {
                    openDatePickerDialog(context) { selectedDate ->
                        applicationDeadline = selectedDate
                    }
                }) {
                    Icon(Icons.Filled.CalendarToday, contentDescription = "Date Picker")
                }
            }
        )

        Text(
            text = errorMessage,
            color = if (isFormValid) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Button(
            onClick = { handleFormSubmit() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Post Opportunity", color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}
