package bav.astrobirthday.ui.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import bav.astrobirthday.ui.theme.AstroBirthdayTheme

@Composable
fun WelcomeScreen() {
    Image(
        painter = painterResource(id = R.drawable.stars_background),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.stars_background),
                contentScale = ContentScale.FillBounds
            )
    )

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (column) = createRefs()
        val topGuideline = createGuidelineFromTop(0.1f)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.7f)
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
                text = stringResource(id = R.string.welcome_title),
                textAlign = TextAlign.Center,
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

            Button(
                onClick = { },
                modifier = Modifier
            ) {
                Text(text = stringResource(id = R.string.welcome_button_text))
            }
        }
    }
}

@Composable
@Preview(device = Devices.PIXEL)
fun SetupScreenPreview() {
    AstroBirthdayTheme {
        WelcomeScreen()
    }
}