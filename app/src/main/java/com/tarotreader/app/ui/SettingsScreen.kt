package com.tarotreader.app.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tarotreader.app.AppSettings
import com.tarotreader.app.model.AppViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PersonalSettingsScreen(
    navController: NavHostController? = null,
    appViewModel: AppViewModel
) {
    val appDataStore = appViewModel.appSettingsFlow.collectAsState(
        initial = AppSettings()
    )

    var name by remember { mutableStateOf(appDataStore.value.userName) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedGender by remember { mutableStateOf("") }
    var showDatePickerDialog by remember { mutableStateOf(false) }
    var enableNameEdit by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = if(enableNameEdit) {""} else name,
            onValueChange = { name = it },
            label = { Text("Name")},
            readOnly = enableNameEdit,
            singleLine = true,
            suffix = {
                IconButton(onClick = {
                    enableNameEdit = !enableNameEdit
                }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
            }
        )

        // Date Picker
        val datePickerState = rememberDatePickerState(initialSelectedDateMillis =selectedDate.atStartOfDay(
            ZoneId.systemDefault()).toInstant().toEpochMilli())



        Button(onClick = { showDatePickerDialog = true }) {
            Text("Select Date of Birth: ${selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))}")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Gender Selection (e.g., using a dropdown)

        val genderOptions = listOf("Male", "Female", "Other")

        DropdownList(
            options = genderOptions,
            selectedOption = selectedGender,
            onOptionSelected = { selectedGender = it }
        )

        Spacer(modifier = Modifier.weight(1f)) // Push button to the bottom

        if (showDatePickerDialog) {
            DatePickerDialog(
                onDismissRequest = { showDatePickerDialog = false },
                confirmButton = {
                    Button(onClick = {
                        selectedDate = datePickerState.selectedDateMillis?.let {
                            Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
                        } ?: LocalDate.now()
                        showDatePickerDialog = false
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDatePickerDialog = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownList(
    options: List<String>,
    selectedOption:String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            readOnly = true,
            value = selectedOption,
            onValueChange = { },
            label = { Text("Select an option") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        onOptionSelected(selectionOption)
                        expanded = false
                    },
                    text = { Text(selectionOption) }
                )
            }
        }
    }
}

@Composable
fun ParameterField(
    name: String,
    currentValue: String,
    postback: (String) -> Unit = {}
) {
    var value = currentValue
    var enableEdit by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Text("$name:")
        if (currentValue == "" || enableEdit) {
            OutlinedTextField(
                value = currentValue,
                onValueChange = { value = it },
                label = { Text(name)},
                suffix = {
                    IconButton(onClick = {
                        enableEdit = !enableEdit
                    }) {
                        Icon(Icons.Default.AddCircle, contentDescription = "Edit")
                    }
                }
            )
        } else {
            OutlinedTextField(
                value = value,
                onValueChange = { value = it },
                label = { Text(name)},
                suffix = {
                    IconButton(onClick = {
                        enableEdit = !enableEdit
                    }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                }
            )
        }
    }
}