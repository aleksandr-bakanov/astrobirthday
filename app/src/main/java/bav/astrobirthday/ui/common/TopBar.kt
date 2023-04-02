package bav.astrobirthday.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bav.astrobirthday.R
import bav.astrobirthday.ui.theme.AstroBirthdayTheme

@Composable
fun TopBar(
    title: String,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Text(
            text = title,
            color = colorResource(id = R.color.white2),
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(start = 20.dp),
        )
    }
}

@Preview
@Composable
fun TopBarPreview() {
    AstroBirthdayTheme {
        TopBar(title = "Solar system")
    }
}
