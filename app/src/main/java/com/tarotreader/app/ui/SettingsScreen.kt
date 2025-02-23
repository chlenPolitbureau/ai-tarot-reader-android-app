package com.tarotreader.app.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tarotreader.app.model.AppViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PersonalSettingsScreen(
    navController: NavHostController? = null,
    appViewModel: AppViewModel
) {
    val appDataStore = appViewModel.uiState.collectAsState()

    var name by remember { mutableStateOf(appDataStore.value.userName) }
    var selectedDate by remember { mutableStateOf(appDataStore.value.dateOfBirth?: 1740133260000) }
    var selectedGender by remember { mutableStateOf(appDataStore.value.gender) }

    PersonalSettingsScreenWrapper(
        name = name,
        dayOfBirth = selectedDate,
        gender = selectedGender,
        appViewModel = appViewModel
    )
}

@Composable
fun PersonalSettingsScreenWrapper(
    name: String,
    dayOfBirth: Long,
    gender: String,
    appViewModel: AppViewModel
) {
    var name by remember { mutableStateOf(name) }
    var dayOfBirth by remember { mutableStateOf<Long>(dayOfBirth) }
    var gender by remember { mutableStateOf(gender) }

    val scope = rememberCoroutineScope()

    fun updateGender(
        newGender: String
    ) {
        gender = newGender
    }

    fun updateDateOfBirth(
        newDateOfirth: Long
    ) {
        dayOfBirth = newDateOfirth
    }

    fun updateName(
        newName: String
    ) {
        name = newName
    }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Row {
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    updateName(name) },
                placeholder = { Text(text = "Enter your name") },
                label = { Text(text = "Name") },
                trailingIcon = {
                    Icon(Icons.Default.Edit, contentDescription = "Edit name")
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Row {
            DatePickerFieldToModal(
                dayOfBirthMillis = dayOfBirth,
                postback = ::updateDateOfBirth
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            GenderRadioButton(
                postback = ::updateGender
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row (
            horizontalArrangement = Arrangement.Center
        ) {
            ElevatedButton(onClick = {
                scope.launch {
                    appViewModel.updatePersonalSettings(
                        name = name,
                        gender = gender,
                        dateOfBirth = dayOfBirth
                    )
                }
            }) {
                Text(text = "Save")
            }
        }
    }
}

@Composable
fun DatePickerFieldToModal(
    dayOfBirthMillis: Long,
    modifier: Modifier = Modifier,
    postback: (Long) -> Unit
) {
    var selectedDate by remember { mutableStateOf<Long>(dayOfBirthMillis) }
    var showModal by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = convertMillisToDate(selectedDate),
        onValueChange = { },
        label = { Text("Date of Birth") },
        placeholder = { Text("MM/DD/YYYY") },
        trailingIcon = {
            Icon(Icons.Default.DateRange, contentDescription = "Select date")
        },
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(selectedDate) {
                awaitEachGesture {
                    // Modifier.clickable doesn't work for text fields, so we use Modifier.pointerInput
                    // in the Initial pass to observe events before the text field consumes them
                    // in the Main pass.
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null) {
                        showModal = true
                    }
                }
            }
    )

    if (showModal) {
        DatePickerModal(
            onDateSelected = {
                selectedDate = it
                postback(selectedDate)},
            onDismiss = { showModal = false }
        )
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                datePickerState.selectedDateMillis?.let { onDateSelected(it) }
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Composable
fun GenderRadioButton(
    postback: (String) -> Unit
) {
    var selectedGender = remember { mutableStateOf("") }
    val radioOptions = listOf("Male", "Female")
    Column {
        radioOptions.forEach {
            text ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (text == selectedGender.value),
                        onClick = {
                            selectedGender.value = text
                            postback(text)
                        }
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedGender.value ),
                    onClick = {
                        selectedGender.value = text
                        postback(text)
                    }
                )
                Text(
                    text = text,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}