package com.cameraxdemo

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.cameraxdemo.databinding.ActivityMainBinding
import com.cameraxdemo.utils.cameraPermissionRequest
import com.cameraxdemo.utils.isPermissionGranted
import com.cameraxdemo.utils.openPermissionSetting

class MainActivity : AppCompatActivity() {

    private val cameraPermission = android.Manifest.permission.CAMERA
    private lateinit var binding: ActivityMainBinding

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                startScanner()
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.buttonOpenScanner.setOnClickListener {
            requestCameraAndStartScanner()
        }
    }


    private fun requestCameraAndStartScanner() {
        if (isPermissionGranted(cameraPermission)) {
            startScanner()
        } else {
            requestCameraPermission()
        }
    }


    private fun requestCameraPermission() {
        when {
            shouldShowRequestPermissionRationale(cameraPermission) -> {
                cameraPermissionRequest(
                    positive = { openPermissionSetting() }
                )
            }
            else -> {
                requestPermissionLauncher.launch(cameraPermission)
            }
        }
    }

    private fun startScanner() {
        ScannerActivity.starScanner(this) {

        }
    }
}












   /* companion object {
        private val TAG = ScannerActivity::class.simpleName
        private var onScan: ((barcodes: List<Barcode>) -> Unit)? = null

        fun startScanner(context: Context, onScan: (barcodes: List<Barcode>) -> Unit) {
            this.onScan = onScan
            Intent(context, ScannerActivity::class.java).also {
                context.startActivity(it)
            }
        }
    }*/
