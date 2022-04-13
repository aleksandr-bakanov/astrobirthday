package bav.astrobirthday.ui.setup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bav.astrobirthday.R
import bav.astrobirthday.ui.theme.AstroBirthdayTheme

@Composable
fun SetupScreen(viewModel: SetupViewModel) {
    val state by viewModel.state.observeAsState()
    state?.let {
        SetupScreen(
            it,
            viewModel::setDate,
            viewModel::submitDate
        )
    }
}

@Composable
fun SetupScreen(
    state: SetupUiState,
    onDateChange: (String) -> Unit,
    onDateSubmit: () -> Unit
) {
    AstroBirthdayTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 32.dp)
            ) {
                Row {
                    OutlinedTextField(
                        value = state.date,
                        onValueChange = onDateChange,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        label = { Text("Birthday") },
                        placeholder = { Text("yyyy-MM-dd") },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
                Row {
                    Text(
                        text = stringResource(R.string.birthday_thresholds_disclaimer),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                }
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        onClick = onDateSubmit
                    ) {
                        Text(text = stringResource(R.string.set_your_birthday))
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
            }

        }
    }
}

@Composable
@Preview
fun SetupScreenPreview() {
    SetupScreen(
        SetupUiState("2022-04-13", true),
        {},
        {}
    )
}