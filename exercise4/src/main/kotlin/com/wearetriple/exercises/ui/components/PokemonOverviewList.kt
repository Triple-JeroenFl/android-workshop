package com.wearetriple.exercises.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.wearetriple.exercises.ui.model.display.PokemonOverviewDisplay
import com.wearetriple.exercises.ui.theme.Dimensions

@Composable
fun PokemonOverviewList(
    display: PokemonOverviewDisplay,
    isLoadingMore: Boolean,
    onFetchMoreData: () -> Unit,
    onFavorite: (String) -> Unit,
    onUnFavorite: (String) -> Unit
) {
    val listState = rememberLazyListState()
    val lastVisibleIndex = remember { derivedStateOf { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index } }
    LaunchedEffect(key1 = lastVisibleIndex.value) {
        lastVisibleIndex.value?.let {
            if(display.list.size - it < 6) onFetchMoreData()
        }
    }
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimensions.Paddings.x_large)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        itemsIndexed(display.list) { index, item ->
            PokemonOverviewListItem(
                display = item,
                onFavorite = onFavorite,
                onUnFavorite = onUnFavorite
            )
            if (index != display.list.lastIndex) Spacer(Modifier.height(Dimensions.Paddings.large))
        }
        if (isLoadingMore) {
            item {
                Spacer(Modifier.height(Dimensions.Paddings.large))
                LoadingMoreIndicator()
            }
        }
    }
}

@Composable
private fun LoadingMoreIndicator() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.Black,
                shape = RoundedCornerShape(Dimensions.Paddings.large)
            )
            .padding(Dimensions.Paddings.large),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(Dimensions.Paddings.xxx_large),
            color = Color.White
        )
    }
}