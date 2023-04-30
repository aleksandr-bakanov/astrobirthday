package bav.astrobirthday.ui.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bav.astrobirthday.R
import bav.astrobirthday.common.PlanetType
import bav.astrobirthday.data.entities.Config
import bav.astrobirthday.domain.model.PlanetAndInfo
import bav.astrobirthday.ui.theme.AstroBirthdayTheme
import bav.astrobirthday.ui.theme.BackgroundBlack
import bav.astrobirthday.ui.theme.GrayText
import bav.astrobirthday.utils.getAgeStringShort
import bav.astrobirthday.utils.localDateToString
import bav.astrobirthday.utils.orNa
import bav.astrobirthday.utils.unicodeWrap
import java.time.LocalDate

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val state by viewModel.uiState.observeAsState()
    state?.let {
        HomeScreen(
            uiState = it,
            goToPlanet = viewModel::goToPlanet,
            toggleByDate = viewModel::toggleSortSolarPlanetsByDate
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    goToPlanet: (String) -> Unit,
    toggleByDate: () -> Unit
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
                        text = stringResource(id = R.string.screen_solar_system),
                        color = colorResource(id = R.color.white2),
                        style = MaterialTheme.typography.h6,
                    )
                },
                actions = {
                    IconButton(onClick = toggleByDate) {
                        Icon(
                            tint = MaterialTheme.colors.primary,
                            painter = painterResource(
                                id = if (uiState.isByDate) R.drawable.ic_sort_bd_filled
                                else R.drawable.ic_sort_bd
                            ),
                            contentDescription = null,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            )
        },
        backgroundColor = Color.Transparent
    ) { paddingValues ->
        val lazyListState = rememberLazyListState()
        LazyColumn(
            state = lazyListState,
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 8.dp),
        ) {
            items(
                items = uiState.planets,
                key = { it.planet.planetName }
            ) {
                PlanetItem(
                    item = it,
                    isByDate = uiState.isByDate,
                    goToPlanet = goToPlanet,
                    modifier = Modifier.animateItemPlacement()
                )
            }
            item { Spacer(modifier = Modifier.height(56.dp)) }
        }
    }
}

private val CANVAS_MODIFIER_LTR = Modifier.fillMaxSize()
private val CANVAS_MODIFIER_RTL = Modifier
    .fillMaxSize()
    .scale(-1f, 1f)

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun PlanetItem(
    item: PlanetAndInfo,
    isByDate: Boolean,
    goToPlanet: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val layoutDirection = LocalLayoutDirection.current
    Card(
        backgroundColor = colorResource(id = R.color.backgroundBlack1),
        elevation = 0.dp,
        shape = RoundedCornerShape(20.dp),
        onClick = { goToPlanet(item.planet.planetName) },
        modifier = modifier
            .fillMaxWidth()
            .height(140.dp)
    ) {
        Box {
            val painter = painterResource(id = item.planetType!!.imageResId)
            val canvasModifier = if (layoutDirection == LayoutDirection.Ltr) {
                CANVAS_MODIFIER_LTR
            } else {
                CANVAS_MODIFIER_RTL
            }
            Canvas(modifier = canvasModifier) {
                val brush = Brush.horizontalGradient(
                    colors = listOf(BackgroundBlack, Color.Transparent),
                    startX = size.width / 2.5f
                )
                val imageSize = painter.intrinsicSize
                val rHeight = size.height
                val rWidth = (imageSize.width / imageSize.height) * rHeight
                val translateX = size.width - (rWidth / 2) - (rHeight / 8)
                translate(left = translateX) {
                    with(painter) {
                        draw(Size(rWidth, rHeight))
                    }
                }
                drawRect(
                    brush = brush,
                    size = Size(width = size.width / 0.85f, height = size.height)
                )
            }

            val titleColor by animateColorAsState(targetValue = if (isByDate) colorResource(id = R.color.white2) else MaterialTheme.colors.primary)
            val subtitleColor by animateColorAsState(targetValue = if (isByDate) MaterialTheme.colors.primary else GrayText)
            Text(
                text = item.planet.getPlanetName(LocalContext.current).unicodeWrap(),
                fontSize = 24.sp,
                color = titleColor,
                modifier = Modifier
                    .padding(start = 20.dp, top = 20.dp)
                    .align(Alignment.TopStart)
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.Center)
            ) {
                Text(
                    text = LocalContext.current.getAgeStringShort(item.ageOnPlanet),
                    fontSize = 18.sp,
                    color = colorResource(id = R.color.white2),
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.nearestBirthday?.let {
                        stringResource(
                            R.string.next_birthday,
                            LocalContext.current.localDateToString(it)
                        )
                    }.orNa(),
                    style = MaterialTheme.typography.caption,
                    fontSize = 12.sp,
                    color = subtitleColor
                )
            }
        }
    }
}

@Preview
@Composable
fun PlanetItemPreview() {
    AstroBirthdayTheme {
        PlanetItem(
            item = PlanetAndInfo(
                planet = Config.solarPlanets[0],
                isFavorite = true,
                ageOnPlanet = 1.23,
                planetType = PlanetType.MERCURY,
                nearestBirthday = LocalDate.now()
            ),
            isByDate = true,
            goToPlanet = { }
        )
    }
}


