package com.kopidannyemil.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kopidannyemil.app.AppViewModel
import com.kopidannyemil.app.data.DummyData
import com.kopidannyemil.app.data.toRupiah
import com.kopidannyemil.app.ui.components.*
import com.kopidannyemil.app.ui.theme.*

@Composable
fun AddressScreen(vm: AppViewModel, nav: NavController, onOrder: () -> Unit) {
    var name by remember { mutableStateOf(vm.userName) }
    var address by remember { mutableStateOf("") }
    var landmark by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Scaffold(
        containerColor = White,
        bottomBar = { AppBottomBar("address", vm.totalItems, nav) }
    ) { pad ->
        GradientBackground(Modifier.padding(pad)) {
            Column(
                Modifier.fillMaxSize().statusBarsPadding().imePadding()
                    .verticalScroll(rememberScrollState()).padding(28.dp)
            ) {
                Spacer(Modifier.height(12.dp))
                Text("Alamat", style = MaterialTheme.typography.headlineLarge, color = Blue900)
                Text("Alamat pengiriman pesananmu", style = MaterialTheme.typography.bodyLarge, color = InkSoft)
                Spacer(Modifier.height(20.dp))

                // Ringkasan pesanan
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(3.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Ringkasan Pesanan", style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(8.dp))
                        if (vm.cart.isEmpty()) {
                            Text(
                                "Belum ada pesanan. Pilih menu di halaman Home terlebih dahulu.",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        } else {
                            vm.cart.entries.sortedBy { it.key }.forEach { (id, qty) ->
                                val m = DummyData.menu.first { it.id == id }
                                Row(Modifier.fillMaxWidth().padding(vertical = 3.dp)) {
                                    Text("${m.emoji} ${m.name} × $qty", Modifier.weight(1f))
                                    Text((m.price * qty).toRupiah(), fontWeight = FontWeight.SemiBold)
                                }
                            }
                            HorizontalDivider(Modifier.padding(vertical = 8.dp), color = Blue200)
                            Row(Modifier.fillMaxWidth()) {
                                Text("Total", Modifier.weight(1f), fontWeight = FontWeight.Bold)
                                Text(vm.totalPrice.toRupiah(), color = Blue700, fontWeight = FontWeight.ExtraBold)
                            }
                        }
                    }
                }

                Spacer(Modifier.height(20.dp))
                AppTextField(name, { name = it; error = null }, "Nama Lengkap", icon = Icons.Default.Badge)
                Spacer(Modifier.height(12.dp))
                AppTextField(address, { address = it; error = null }, "Alamat", icon = Icons.Default.Home, minLines = 3)
                Spacer(Modifier.height(12.dp))
                AppTextField(
                    landmark, { landmark = it }, "Patokan atau detail lengkap",
                    icon = Icons.Default.LocationOn, minLines = 3
                )
                Spacer(Modifier.height(10.dp))
                ErrorText(error)
                Spacer(Modifier.height(20.dp))
                PrimaryButton("Order dan Kirim", {
                    when {
                        vm.cart.isEmpty() -> error = "Keranjang masih kosong"
                        name.isBlank() || address.isBlank() -> error = "Nama dan alamat wajib diisi"
                        else -> {
                            vm.checkout(address.trim() + if (landmark.isNotBlank()) " (${landmark.trim()})" else "")
                            onOrder()
                        }
                    }
                })
            }
        }
    }
}
