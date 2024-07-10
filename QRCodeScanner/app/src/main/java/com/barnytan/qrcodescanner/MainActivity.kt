package com.barnytan.qrcodescanner

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.barnytan.qrcodescanner.ui.theme.QRCodeScannerTheme

class MainActivity : ComponentActivity() {
    private var rawValue = mutableStateOf("")
    private val tag = "QR Scanner"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val scanner = GmsBarcodeScanning.getClient(this, scannerOptions())

        setContent {
            QRCodeScannerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainComposable {
                        runScanner(scanner)
                    }
                }
            }
        }
    }

    private fun scannerOptions(): GmsBarcodeScannerOptions {
        return GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE
            )
            .enableAutoZoom()
            .build()
    }

    private fun runScanner(scanner: GmsBarcodeScanner) {
        scanner.startScan()
            .addOnSuccessListener { barcode ->
                rawValue.value = barcode.rawValue.toString()
                Log.d(tag, "initiateScanner: Raw Value $rawValue")
            }
    }

    @Composable
    fun MainComposable(onClick: () -> Unit) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Text(text = "QR Code Scanner", fontSize = 30.sp, fontWeight = FontWeight.Black)
            Spacer(modifier = Modifier.height(60.dp))
            var rawText by rawValue
            OutlinedTextField(
                rawText,
                onValueChange = { newText -> rawText = newText },
                modifier = Modifier.fillMaxWidth(0.8F),
                readOnly = true,
                label = {
                    Text(text = "Scanned Data Result")
                }
            )
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedButton(
                    onClick = onClick
                ) {
                    Text(text = "Scan QR")
                }
            }
        }
    }

    @Composable
    fun QRresultsComposable() {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        }
    }

    @Preview(showBackground = true)
    @Composable
    fun MainComposablePreview() {
        QRCodeScannerTheme {
            MainComposable {

            }
        }
    }
}



