package bav.astrobirthday.ui.favorites

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bav.astrobirthday.R
import bav.astrobirthday.common.PlanetType
import bav.astrobirthday.domain.model.PlanetAndInfo
import bav.astrobirthday.ui.common.opengl.getPlanetTextureId
import bav.astrobirthday.ui.common.opengl.planetImagesByTextureId
import bav.astrobirthday.utils.getAgeStringShort

@Composable
fun FavoritesScreen(viewModel: FavoritesViewModel) {
    val state by viewModel.uiState.observeAsState()
    state?.let {
        FavoritesScreen(
            uiState = it,
            goToPlanet = viewModel::goToPlanet
        )
    }
}

@Composable
fun FavoritesScreen(
    uiState: FavoritesUiState,
    goToPlanet: (String) -> Unit
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
                        text = stringResource(id = R.string.title_favorites),
                        color = colorResource(id = R.color.white2),
                        style = MaterialTheme.typography.h6,
                    )
                }
            )
        },
        backgroundColor = Color.Transparent
    ) { paddingValues ->
        if (uiState.planets.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(id = R.string.empty_favorites_placeholder_text),
                    color = colorResource(id = R.color.grayText),
                    style = MaterialTheme.typography.caption,
                )
            }
        } else {
            LazyVerticalGrid(
                contentPadding = paddingValues,
                columns = GridCells.Fixed(count = 2),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(horizontal = 8.dp),
            ) {
                items(uiState.planets) {
                    PlanetItem(
                        item = it,
                        goToPlanet = goToPlanet
                    )
                }
                item(
                    span = { GridItemSpan(2) }
                ) {
                    Spacer(modifier = Modifier.height(56.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun PlanetItem(
    item: PlanetAndInfo,
    goToPlanet: (String) -> Unit
) {
    Card(
        backgroundColor = colorResource(id = R.color.backgroundBlack1),
        elevation = 0.dp,
        shape = RoundedCornerShape(20.dp),
        onClick = { goToPlanet(item.planet.planetName) },
        modifier = Modifier
            .aspectRatio(
                ratio = 1f,
                matchHeightConstraintsFirst = true
            )
    ) {
        val imageResId = when {
            item.planetType == PlanetType.SATURN -> R.drawable.pic_saturn_5
            item.planetType != null -> item.planetType.imageResId
            else -> planetImagesByTextureId[getPlanetTextureId(item.planet.planetName)]
        }

        imageResId?.let { resId ->
            val painter = painterResource(id = resId)
            Canvas(modifier = Modifier.fillMaxSize()) {
                val imageSize = painter.intrinsicSize
                val rWidth = size.width
                val rHeight = (imageSize.height / imageSize.width) * rWidth
                val translateY = size.height / 2.5f
                translate(top = translateY) {
                    with(painter) {
                        draw(Size(rWidth, rHeight))
                    }
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = item.planet.planetName,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = colorResource(id = R.color.white2),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
            Text(
                text = LocalContext.current.getAgeStringShort(item.ageOnPlanet),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                color = colorResource(id = R.color.grayText),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
        }
    }
}