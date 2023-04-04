package bav.astrobirthday.ui.home

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bav.astrobirthday.R
import bav.astrobirthday.common.PlanetType
import bav.astrobirthday.data.entities.Config
import bav.astrobirthday.domain.model.PlanetAndInfo
import bav.astrobirthday.ui.theme.AstroBirthdayTheme
import bav.astrobirthday.utils.getAgeStringForMainScreen
import bav.astrobirthday.utils.localDateToString
import bav.astrobirthday.utils.orNa
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
        LazyColumn(
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 8.dp),
        ) {
            items(uiState.planets) {
                PlanetItem(
                    item = it,
                    isByDate = uiState.isByDate,
                    goToPlanet = goToPlanet
                )
            }
            item { Spacer(modifier = Modifier.height(56.dp)) }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun PlanetItem(
    item: PlanetAndInfo,
    isByDate: Boolean,
    goToPlanet: (String) -> Unit
) {
    Card(
        backgroundColor = colorResource(id = R.color.backgroundBlack1),
        elevation = 0.dp,
        shape = RoundedCornerShape(20.dp),
        onClick = { goToPlanet(item.planet.planetName) },
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
    ) {
        Box {
            Text(
                text = item.planet.planetName,
                fontSize = 24.sp,
                color = if (isByDate) colorResource(id = R.color.white2) else MaterialTheme.colors.primary,
                modifier = Modifier
                    .padding(start = 20.dp, top = 20.dp)
                    .align(Alignment.TopStart)
            )
            Image(
                painter = painterResource(id = item.planetType!!.imageResId),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterEnd)
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.Center)
            ) {
                Text(text = LocalContext.current.getAgeStringForMainScreen(item.ageOnPlanet))
                Text(
                    text = item.nearestBirthday?.let { LocalContext.current.localDateToString(it) }
                        .orNa(),
                    color = if (isByDate) MaterialTheme.colors.primary else colorResource(id = R.color.white2)
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


