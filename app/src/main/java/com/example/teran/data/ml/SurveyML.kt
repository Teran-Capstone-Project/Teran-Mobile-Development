package com.example.teran.data.ml

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import com.google.android.gms.tflite.client.TfLiteInitializationOptions
import com.google.android.gms.tflite.gpu.support.TfLiteGpu
import com.google.android.gms.tflite.java.TfLite
import org.tensorflow.lite.InterpreterApi
import org.tensorflow.lite.gpu.GpuDelegateFactory
import java.io.FileInputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class SurveyML(
    private val modelName: String = "model.tflite",
    val context: Context,
    private val onResult: (String) -> Unit,
    private val onError: (String) -> Unit
) {

    private var interpreter: InterpreterApi? = null

    private var isGPUSupported: Boolean = false

    init {
        TfLiteGpu.isGpuDelegateAvailable(context).onSuccessTask { gpuAvailable ->
            val optionsBuilder = TfLiteInitializationOptions.builder()
            if (gpuAvailable) {
                optionsBuilder.setEnableGpuDelegateSupport(true)
                isGPUSupported = true
            }
            TfLite.initialize(context, optionsBuilder.build())
        }.addOnSuccessListener {
            loadLocalModel()
        }.addOnFailureListener {
            println("addOnFailureListener: " + it)
        }
    }

    fun predict(
        jenisKelamin: Int,
        umur: Int,
        jurusan: Int,
        tahunStudi: Int,
        ipk: Int,
        sudahMenikah: Int,
        sedangDepresi: Int,
        sedangCemas: Int,
        sedangPanik: Int,
        sedangMenjalaniPerawatan: Int
    ) {
        // Konversi input ke FloatArray atau sesuaikan dengan kebutuhan model
        val inputArray = floatArrayOf(
            jenisKelamin.toFloat(),
            umur.toFloat(),
            jurusan.toFloat(),
            tahunStudi.toFloat(),
            ipk.toFloat(),
            sudahMenikah.toFloat(),
            sedangDepresi.toFloat(),
            sedangCemas.toFloat(),
            sedangPanik.toFloat(),
            sedangMenjalaniPerawatan.toFloat()
        )

        try {
            val outputArray = Array(1) { FloatArray(1) }
            interpreter?.run(inputArray, outputArray)
            onResult(outputArray[0][0].toString())
        } catch (e: Exception) {
            onError("predict (catch): " + e.message)
        }
    }

    private fun loadLocalModel() {
        try {
            val buffer: ByteBuffer = loadModelFile(context.assets, modelName)
            initializeInterpreter(buffer)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }

    private fun initializeInterpreter(model: Any) {
        interpreter?.close()
        try {
            val options = InterpreterApi.Options()
                .setRuntime(InterpreterApi.Options.TfLiteRuntime.FROM_SYSTEM_ONLY)

            if (isGPUSupported) {
                options.addDelegateFactory(GpuDelegateFactory())
            }
            if (model is ByteBuffer) {
                interpreter = InterpreterApi.create(model, options)
            }

        } catch (e: Exception) {
            println("initializeInterpreter (catch): " + e.message)
        }
    }

    private fun loadModelFile(assetManager: AssetManager, modelPath: String): MappedByteBuffer {
        assetManager.openFd(modelPath).use { fileDescriptor ->
            FileInputStream(fileDescriptor.fileDescriptor).use { inputStream ->
                val fileChannel = inputStream.channel
                val startOffset = fileDescriptor.startOffset
                val declaredLength = fileDescriptor.declaredLength
                return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
            }
        }
    }

    fun close() {
        interpreter?.close()
    }
}