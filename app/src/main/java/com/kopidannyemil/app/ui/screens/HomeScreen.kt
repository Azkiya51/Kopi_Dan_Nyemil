package com.kopidannyemil.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kopidannyemil.app.AppViewModel
import com.kopidannyemil.app.data.DummyData
import com.kopidannyemil.app.data.MenuItem
import com.kopidannyemil.app.data.toRupiah
import com.kopidannyemil.app.ui.components.AppBottomBar
import com.kopidannyemil.app.ui.theme.*

@Composable
fun HomeScreen(vm: AppViewModel, nav: NavController) {
    var category by remember { mutableStateOf("Semua") }
    val items = remember(category) {
        if (category == "Semua") DummyData.menu else DummyData.menu.filter { it.category == category }
    }

    Scaffold(
        containerColor = Blue50,
        bottomBar = {
            Column {
                if (vm.totalItems > 0) CartBar(vm.totalItems, vm.totalPrice) { nav.navigate("address") }
                AppBottomBar("home", vm.totalItems, nav)
            }
        }
    ) { pad ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(pad),
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Box(
                    Modifier.fillMaxWidth()
                        .background(
                            Brush.linearGradient(listOf(Blue500, Blue700)),
                            RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                        )
                        .statusBarsPadding()
                        .padding(horizontal = 24.dp, vertical = 28.dp)
                ) {
                    Column {
                        Text("Halo ${vm.userName},", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = White)
                        Spacer(Modifier.height(4.dp))
                        Text("Mau ngopi atau nyemil apa hari ini?", color = Blue100, fontSize = 15.sp)
                    }
                }
            }
            item {
                Text(
                    "Menu", style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
                )
                Row(
                    Modifier.padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DummyData.categories.forEach { c ->
                        FilterChip(
                            selected = category == c,
                            onClick = { category = c },
                            label = { Text(c) },
                            shape = RoundedCornerShape(50),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Blue600,
                                selectedLabelColor = White,
                                containerColor = White
                            )
                        )
                    }
                }
            }
            items(items, key = { it.id }) { item ->
                MenuCard(
                    item = item,
                    qty = vm.cart[item.id] ?: 0,
                    onAdd = { vm.add(item.id) },
                    onRemove = { vm.remove(item.id) }
                )
            }
        }
    }
}

@Composable
private fun MenuCard(item: MenuItem, qty: Int, onAdd: () -> Unit, onRemove: () -> Unit) {
    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(3.dp),
        modifier = Modifier.padding(horizontal = 20.dp).fillMaxWidth()
    ) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.image)
                    .crossfade(true)
                    .build(),
                contentDescription = item.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(84.dp).clip(RoundedCornerShape(18.dp)),
                loading = {
                    Box(Modifier.fillMaxSize().background(Blue100), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(Modifier.size(24.dp), color = Blue600, strokeWidth = 2.dp)
                    }
                },
                error = {
                    Box(
                        Modifier.fillMaxSize().background(Brush.linearGradient(listOf(Blue100, Blue200))),
                        contentAlignment = Alignment.Center
                    ) { Text(item.emoji, fontSize = 36.sp) }
                }
            )
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text(item.name, style = MaterialTheme.typography.titleMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(item.description, style = MaterialTheme.typography.bodyMedium, maxLines = 2)
                Spacer(Modifier.height(6.dp))
                Text(item.price.toRupiah(), color = Blue700, fontWeight = FontWeight.Bold)
            }
            if (qty == 0) {
                FilledIconButton(
                    onClick = onAdd,
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = Blue600)
                ) { Icon(Icons.Default.Add, "Tambah", tint = White) }
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    SmallRound(Icons.Default.Remove, onRemove)
                    Text("$qty", fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp))
                    SmallRound(Icons.Default.Add, onAdd)
                }
            }
        }
    }
}

@Composable
private fun SmallRound(icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Box(
        Modifier.size(30.dp).background(Blue100, CircleShape).clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) { Icon(icon, null, tint = Blue700, modifier = Modifier.size(18.dp)) }
}

@Composable
private fun CartBar(count: Int, total: Int, onClick: () -> Unit) {
    Row(
        Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
            .background(Blue700, RoundedCornerShape(18.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 18.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.ShoppingBag, null, tint = White)
        Spacer(Modifier.width(10.dp))
        Text("$count item", color = White, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.weight(1f))
        Text("${total.toRupiah()}  •  Lihat pesanan", color = White, fontWeight = FontWeight.Bold)
    }
}