package com.example.amam

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.camera.view.PreviewView
import com.google.common.util.concurrent.ListenableFuture
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var previewView: PreviewView
    private lateinit var captureButton: Button
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var imageCapture: ImageCapture

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        previewView = findViewById(R.id.previewView)
        captureButton = findViewById(R.id.buttonCapture)

        // Solicitar permisos de cámara
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_DENIED) {
            requestCameraPermission.launch(Manifest.permission.CAMERA)
        } else {
            startCamera()
        }

        captureButton.setOnClickListener {
            // Lógica para capturar la foto
            captureImage()
        }

        // Habilitar el modo Edge-to-Edge ajustando los márgenes
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private val requestCameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                startCamera()
            } else {
                // Manejo de permisos denegados
            }
        }

    private fun startCamera() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            // Crear el preview de la cámara
            val preview = Preview.Builder()
                .build()

            // Configurar selector de cámara (en este caso, cámara trasera)
            val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()

            // Crear ImageCapture
            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)  // Modo de captura optimizado
                .build()

            // Enlazar el ciclo de vida de la cámara al proceso
            cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)

            // Asignar el SurfaceProvider del PreviewView para mostrar la vista previa
            preview.setSurfaceProvider(previewView.surfaceProvider)

        }, ContextCompat.getMainExecutor(this))
    }


    private fun captureImage() {
        // Crear un archivo donde guardar la imagen capturada (por ejemplo, en el almacenamiento interno)
        val photoFile = File(externalCacheDir, "captured_photo.jpg")
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Tomar la foto
        imageCapture.takePicture(
            outputOptions, ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    // Aquí puedes cargar la imagen tomada en el ImageView
                    val photoUri = outputFileResults.savedUri
                    imageView.setImageURI(photoUri)  // Mostrar la imagen capturada en el ImageView
                }

                override fun onError(exception: ImageCaptureException) {
                    // Manejar el error
                    exception.printStackTrace()
                }
            })
    }

}

