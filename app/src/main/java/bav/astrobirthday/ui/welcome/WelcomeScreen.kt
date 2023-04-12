package bav.astrobirthday.ui.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import bav.astrobirthday.R
import bav.astrobirthday.ui.common.GradientButton
import bav.astrobirthday.ui.theme.AstroBirthdayTheme
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate

@Composable
fun WelcomeScreen(viewModel: WelcomeViewModel) {
    val state by viewModel.state.observeAsState()
    state?.let { uiState ->
        WelcomeScreen(
            uiState = uiState,
            letsGo = viewModel::letsGo,
            setDate = viewModel::setDate,
            submitDate = viewModel::submitDate
        )
    }
}

@Composable
fun WelcomeScreen(
    uiState: WelcomeUiState,
    letsGo: () -> Unit,
    setDate: (LocalDate) -> Unit,
    submitDate: () -> Unit
) {
    val dialogState = rememberMaterialDialogState()

    Image(
        painter = painterResource(id = R.drawable.stars_background),
        contentScale = ContentScale.FillBounds,
        contentDescription = null,
        modifier = Modifier.fillMaxSize()
    )

    when (uiState) {
        WelcomeUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        WelcomeUiState.Welcome -> {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (column) = createRefs()
                val topGuideline = createGuidelineFromTop(0.1f)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(fraction = 0.75f)
                        .constrainAs(column) {
                            top.linkTo(topGuideline)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_bold_color),
                        contentDescription = null,
                        modifier = Modifier
                            .size(60.dp)
                            .paint(painter = painterResource(id = R.drawable.logo_bold_color))
                    )
                    Text(
                        text = getTitle(uiState),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1,
                        fontSize = 18.sp,
                        color = colorResource(id = R.color.white2)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.vector_astronaut),
                        contentDescription = null,
                        modifier = Modifier
                            .size(240.dp)
                            .paint(painter = painterResource(id = R.drawable.vector_astronaut))
                    )
                    GradientButton(
                        text = stringResource(id = R.string.welcome_button_text),
                        modifier = Modifier
                            .fillMaxWidth(fraction = 0.5f),
                        onClick = letsGo
                    )
                }
            }
        }
        is WelcomeUiState.Calendar -> {
            MaterialDialog(
                dialogState = dialogState,
                buttons = {
                    positiveButton("Ok") {
                        submitDate()
                    }
                },
                onCloseRequest = { }
            ) {
                datepicker(
                    title = getTitle(uiState = uiState).uppercase(),
                    initialDate = uiState.currentDate,
                    yearRange = IntRange(1900, LocalDate.now().year),
                    allowedDateValidator = {
                        val now = LocalDate.now()
                        it.isBefore(now) || it.isEqual(now)
                    }
                ) { date ->
                    setDate(date)
                }
            }
            dialogState.show()
        }
    }
}

@Composable
private fun getTitle(uiState: WelcomeUiState): String {
    return when (uiState) {
        WelcomeUiState.Welcome -> stringResource(id = R.string.welcome_title)
        is WelcomeUiState.Calendar -> stringResource(id = R.string.welcome_set_your_birthday_title)
        else -> ""
    }
}

@Composable
@Preview(device = Devices.PIXEL_4_XL)
fun SetupScreenPreview() {
    AstroBirthdayTheme {
        WelcomeScreen(
            uiState = WelcomeUiState.Welcome,
            letsGo = { },
            setDate = { },
            submitDate = { }
        )
    }
}