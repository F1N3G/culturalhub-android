package com.g.culturalhub.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.g.culturalhub.model.Event

private val sampleEvents = listOf(
    Event(1, "Hamlet", "Teatru", "Teatrul Național", "București", "12 Sep 2026", 60, "https://picsum.photos/seed/hamlet/600/360"),
    Event(2, "Carmen", "Operă", "Opera Română", "Cluj-Napoca", "18 Sep 2026", 90, "https://picsum.photos/seed/carmen/600/360"),
    Event(3, "Concert Simfonic", "Concert", "Sala Radio", "București", "25 Sep 2026", 75, "https://picsum.photos/seed/concert/600/360"),
    Event(4, "Expoziție Brâncuși", "Expoziție", "MNAC", "București", "1 Oct 2026", 30, "https://picsum.photos/seed/brancusi/600/360"),
    Event(5, "Bohème", "Operă", "Opera Timișoara", "Timișoara", "8 Oct 2026", 85, "https://picsum.photos/seed/boheme/600/360"),
    Event(6, "Stand-up Comedy", "Concert", "Arenele Romane", "București", "15 Oct 2026", 50, "https://picsum.photos/seed/comedy/600/360"),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventListScreen(modifier: Modifier = Modifier) {
    val listState = rememberLazyListState()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("🎭 CulturalHub", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .simpleVerticalScrollbar(
                    state = listState,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                ),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(sampleEvents) { event ->
                EventCard(event)
            }
        }
    }
}

@Composable
private fun EventCard(event: Event) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            AsyncImage(
                model = event.imageUrl,
                contentDescription = event.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
            Column(modifier = Modifier.padding(16.dp)) {
                CategoryTag(event.category)
                Spacer(Modifier.height(8.dp))
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "${event.venue}, ${event.city}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = event.date, style = MaterialTheme.typography.bodyMedium)
                    Text(
                        text = "de la ${event.priceFrom} lei",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoryTag(category: String) {
    Surface(
        color = MaterialTheme.colorScheme.tertiaryContainer,
        contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = category,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}

// Bară de scroll mereu vizibilă cât timp lista are ce derula.
private fun Modifier.simpleVerticalScrollbar(
    state: LazyListState,
    color: Color,
    width: Dp = 5.dp
): Modifier = this.drawWithContent {
    drawContent()
    val info = state.layoutInfo
    val totalItems = info.totalItemsCount
    val visible = info.visibleItemsInfo
    if (totalItems > 0 && visible.isNotEmpty()) {
        val first = visible.first()
        val itemSize = first.size.toFloat().coerceAtLeast(1f)
        val totalHeight = itemSize * totalItems
        val viewport = size.height
        if (totalHeight > viewport) {
            val barHeight = (viewport / totalHeight * viewport).coerceIn(24f, viewport)
            val scrolled = itemSize * first.index - first.offset
            val maxScroll = (totalHeight - viewport).coerceAtLeast(1f)
            val barY = (scrolled / maxScroll * (viewport - barHeight))
                .coerceIn(0f, viewport - barHeight)
            drawRoundRect(
                color = color,
                topLeft = Offset(size.width - width.toPx() - 2.dp.toPx(), barY),
                size = Size(width.toPx(), barHeight),
                cornerRadius = CornerRadius(width.toPx() / 2f)
            )
        }
    }
}