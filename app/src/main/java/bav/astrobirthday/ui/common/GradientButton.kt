package bav.astrobirthday.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import bav.astrobirthday.R

@Composable
fun GradientButton(
    text: String,
    modifier: Modifier = Modifier,
    gradient: Brush = blueGradient,
    onClick: () -> Unit = { },
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        shape = RoundedCornerShape(percent = 40),
        contentPadding = PaddingValues(),
        onClick = { onClick() },
    ) {
        Box(
            modifier = Modifier
                .background(gradient)
                .fillMaxWidth()
                .heightIn(min = 40.dp),
            contentAlignment = Alignment.Center,

            ) {
            Text(
                text = text,
                color = colorResource(id = R.color.white2),
                style = MaterialTheme.typography.button,
                textAlign = TextAlign.Center
            )
        }
    }
}

private val blueGradient = Brush.linearGradient(
    colors = listOf(
        Color(64, 205, 180),
        Color(8, 28, 133),
    ),
    start = Offset.Zero,
    end = Offset.Infinite
)
