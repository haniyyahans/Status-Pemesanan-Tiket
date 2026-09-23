package com.example.pemesanantiket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import android.content.Intent
import android.net.Uri
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextField
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.example.pemesanantiket.ui.theme.PemesananTiketTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PemesananTiketTheme {
                var hargaTiket by remember { mutableStateOf(50000) }
                var jumlahTiket by remember { mutableStateOf(1) }
                var namaPembeli by remember { mutableStateOf("") }

                HalamanPesanTiket(
                    hargaTiket = hargaTiket,
                    jumlahTiket = jumlahTiket,
                    namaPembeli = namaPembeli,

                    onJumlahTiketChange = {
                        jumlahTiket = it
                    },

                    onNamaPembeliChange = {
                        namaPembeli = it
                    }
                )
            }
        }
    }
}

@Composable
fun HalamanPesanTiket(
    hargaTiket: Int,
    jumlahTiket: Int,
    namaPembeli: String,
    onJumlahTiketChange: (Int) -> Unit,
    onNamaPembeliChange: (String) -> Unit
) {
    val totalBayar = hargaTiket * jumlahTiket

    // Status awal kosong
    var statusPesanan by remember {
        mutableStateOf("")
    }

    // Menentukan apakah proses konfirmasi sedang berjalan
    var prosesKonfirmasi by remember {
        mutableStateOf(false)
    }

    // LaunchedEffect hanya berjalan ketika prosesKonfirmasi berubah
    LaunchedEffect(prosesKonfirmasi) {

        if (prosesKonfirmasi) {

            // Jika nama kosong
            if (namaPembeli.isBlank()) {

                statusPesanan = "Status : Nama Masih Kosong"

                prosesKonfirmasi = false

            } else {

                // Menampilkan status proses
                statusPesanan = "Status : Memproses pesanan........."

                // Menunggu 5 detik
                delay(5000)

                // Setelah 5 detik
                statusPesanan = "Status : Tiket telah dipesan"

                prosesKonfirmasi = false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Pemesanan Tiket",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Nama Pembeli",
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = namaPembeli,
                onValueChange = onNamaPembeliChange,
                placeholder = {
                    Text("Masukkan nama")
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Harga Tiket",
            fontSize = 18.sp
        )

        Text(
            text = "Rp $hargaTiket",
            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Jumlah Tiket",
            fontSize = 18.sp
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                onClick = {
                    if (jumlahTiket > 1) {
                        onJumlahTiketChange(jumlahTiket - 1)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
            ) {
                Text("-")
            }

            Text(
                text = "$jumlahTiket",
                fontSize = 22.sp,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Button(
                onClick = {
                    onJumlahTiketChange(jumlahTiket + 1)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
            ) {
                Text("+")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Total Bayar",
            fontSize = 18.sp
        )

        Text(
            text = "Rp $totalBayar",
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                prosesKonfirmasi = true
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Konfirmasi Pemesanan Tiket")
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (statusPesanan.isNotEmpty()) {
            Text(
                text = statusPesanan,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
