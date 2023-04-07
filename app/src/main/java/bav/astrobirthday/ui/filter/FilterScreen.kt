package bav.astrobirthday.ui.filter

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import bav.astrobirthday.R
import bav.astrobirthday.data.entities.PlanetSorting
import bav.astrobirthday.data.entities.SortOrder
import bav.astrobirthday.data.entities.invert
import bav.astrobirthday.ui.common.blueGradient
import bav.astrobirthday.ui.common.solidGrayGradient
import bav.astrobirthday.ui.theme.Blue
import bav.astrobirthday.ui.theme.GrayInactive

@Composable
fun FilterScreen(viewModel: FilterViewModel) {
    val state by viewModel.state.observeAsState()
    state?.let {
        FilterScreen(
            state = it,
            onSelectSorting = viewModel::onSelectSorting,
            goBack = viewModel::goBack
        )
    }
}

@Composable
fun FilterScreen(
    state: FilterViewState,
    onSelectSorting: (PlanetSorting) -> Unit,
    goBack: () -> Unit
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
                        text = stringResource(id = R.string.filter_sorting_header),
                        color = colorResource(id = R.color.white2),
                        style = MaterialTheme.typography.h6,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(
                            tint = MaterialTheme.colors.primary,
                            painter = painterResource(id = R.drawable.back_nav_icon),
                            contentDescription = null,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                },
            )
        },
        backgroundColor = Color.Transparent
    ) { paddingValues ->
        Card(
            backgroundColor = colorResource(id = R.color.backgroundDarkGrey),
            elevation = 0.dp,
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .padding(horizontal = 8.dp)
        ) {
            LazyColumn(
                contentPadding = paddingValues,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                items(
                    count = state.items.size,
                ) { index ->
                    SortingItem(
                        item = state.items[index],
                        isSelected = index == state.selectIndex,
                        onClick = onSelectSorting
                    )
                }
            }
        }
    }
}

@Composable
private fun SortingItem(
    item: PlanetSorting,
    isSelected: Boolean,
    onClick: (PlanetSorting) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .border(
                width = 0.75.dp,
                brush = if (isSelected) blueGradient else solidGrayGradient,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                onClick(
                    PlanetSorting(
                        column = item.column,
                        order = if (isSelected) item.order.invert() else item.order
                    )
                )
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(horizontal = 10.dp)
        ) {
            Text(
                text = stringResource(id = item.column.resId),
                color = colorResource(id = R.color.white2),
            )
            val angle by animateFloatAsState(targetValue = if (item.order == SortOrder.ASC) 0f else 180f)
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_up),
                contentDescription = null,
                tint = if (isSelected) Blue else GrayInactive,
                modifier = Modifier
                    .size(28.dp)
                    .rotate(angle)
            )
        }
    }
}