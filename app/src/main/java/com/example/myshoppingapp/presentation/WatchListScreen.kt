package com.example.myshoppingapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myshoppingapp.domain.models.WatchlistItem


@Composable
fun WatchListScreen(
    viewModel: MyViewModel = hiltViewModel(),
    navController: NavController,
) {
    Box(modifier = Modifier.fillMaxSize()) {

        val  state = viewModel.getWatchListState.collectAsState()

        val removeState = viewModel.removeFromWatchListState.collectAsState()

        LaunchedEffect(Unit){
            viewModel.getWatchList()
        }

        // 🔴 Top-right Circle
        Box(
            modifier = Modifier
                .size(345.dp)
                .offset(x = 180.dp, y = (-160).dp)
                .clip(CircleShape)
                .background(Color(0xFFF68B8B)) // Light pink-red
        )

        when {
            state.value.isLoading -> {
                Text("Loading...", modifier = Modifier.padding(20.dp))
            }
            state.value.error.isNotEmpty() -> {
                Text(
                    "Error: ${state.value.error}",
                    color = Color.Red,
                    modifier = Modifier.padding(20.dp)
                )
            }
            state.value.data.isNotEmpty() -> {
                WatchListContent(
                    navController = navController,
                    watchList = state.value.data
                )
            }




        }

        when{
            removeState.value.isLoading ->{
                Text("Loading...", modifier = Modifier.padding(20.dp))
            }
            removeState.value.error.isNotEmpty() ->{
                Text(
                    "Error: ${removeState.value.error}",
                    color = Color.Red,
                    modifier = Modifier.padding(20.dp)
                )
            }
            removeState.value.data != null ->{
                LaunchedEffect(Unit) {
                    viewModel.getWatchList()
                }
            }
        }


    }





}


@Composable
fun WatchListContent(
    navController: NavController,
    watchList: List<WatchlistItem>,
    viewModel: MyViewModel = hiltViewModel()
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFF8F8F8))
        .padding(16.dp)) {

        Text(
            text = "My Watchlist",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (watchList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Your Watchlist is empty!", color = Color.Gray)
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(watchList) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {  },
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(12.dp)
                        ) {
                            AsyncImage(
                                model = item.imageUrl,
                                contentDescription = item.name,
                                modifier = Modifier
                                    .size(70.dp)
                                    .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = item.name,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                item.price?.let {
                                    Text(
                                        text = "₹ $it",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color(0xFF388E3C)
                                    )
                                }

                            }

                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "In Watchlist",
                                tint = Color.Red,
                                modifier = Modifier.clickable {
                                    viewModel.removeFromWatchList(item.productId)

                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun WatchlistItemCard(
    item: WatchlistItem,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.name,
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                item.price?.let {
                    Text(
                        text = "₹ $it",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF388E3C)
                    )
                }

            }

            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "In Watchlist",
                tint = Color.Red
            )
        }
    }
}
