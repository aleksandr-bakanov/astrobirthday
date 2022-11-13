package bav.astrobirthday.ui.setup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import bav.astrobirthday.ui.setup.SetupUiState.DateState
import bav.astrobirthday.ui.theme.AstroBirthdayTheme
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate

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
    onDateChange: (LocalDate) -> Unit,
    onDateSubmit: () -> Unit
) {
    val dialogState = rememberMaterialDialogState()

    AstroBirthdayTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            MaterialDialog(
                dialogState = dialogState,
                buttons = {
                    positiveButton("Ok") {
                        onDateSubmit()
                    }
                },
                onCloseRequest = { }
            ) {
                datepicker(
                    initialDate = state.date,
                    yearRange = IntRange(1900, LocalDate.now().year),
                    allowedDateValidator = {
                        val now = LocalDate.now()
                        it.isBefore(now) || it.isEqual(now)
                    }
                ) { date ->
                    onDateChange(date)
                }
            }
        }
    }

    dialogState.show()
}

@Composable
@Preview
fun SetupScreenPreview() {
    AstroBirthdayTheme {
        SetupScreen(
            SetupUiState(LocalDate.now(), DateState.InFuture),
            {},
            {}
        )
    }
}