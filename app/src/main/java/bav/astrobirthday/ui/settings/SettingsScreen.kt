package bav.astrobirthday.ui.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bav.astrobirthday.R
import bav.astrobirthday.common.Formatters
import bav.astrobirthday.ui.theme.AstroBirthdayTheme
import java.time.LocalDate

@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    val state by viewModel.state.observeAsState()
    state?.let {
        SettingsScreen(
            it,
            viewModel::pickBirthday,
        )
    }
}

@Composable
fun SettingsScreen(
    state: SettingsViewState,
    onBirthdayClick: () -> Unit = {},
) {
    Image(
        painter = painterResource(id = R.drawable.stars_background),
        contentScale = ContentScale.FillBounds,
        contentDescription = null,
        modifier = Modifier.fillMaxSize()
    )

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.Transparent,
                title = {
                    Text(
                        text = stringResource(id = R.string.title_settings),
                        color = colorResource(id = R.color.white2),
                        style = MaterialTheme.typography.h6,
                    )
                }
            )
        },
        backgroundColor = Color.Transparent
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
                    .clickable { onBirthdayClick() }
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.your_birthday),
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Start,
                    color = colorResource(id = R.color.white2),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = Formatters.dateToString(state.birthday),
                    style = MaterialTheme.typography.button,
                    textAlign = TextAlign.End,
                    color = colorResource(id = R.color.white2),
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(id = R.string.credits),
                style = MaterialTheme.typography.caption,
                textAlign = TextAlign.Center,
                color = colorResource(id = R.color.white2),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            )
            Spacer(modifier = Modifier.height(56.dp))
        }
    }

}

@Composable
@Preview
fun SettingsScreenPreview() {
    AstroBirthdayTheme {
        SettingsScreen(
            SettingsViewState(
                birthday = LocalDate.parse("2015-02-02"),
            )
        )
    }
}