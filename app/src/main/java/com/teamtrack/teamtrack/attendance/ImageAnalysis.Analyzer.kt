import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.ImageFormat
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.YuvImage
import android.media.Image
import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import java.io.ByteArrayOutputStream

class BarcodeImageAnalyzer(private val onBarcodeDetected: (String) -> Unit) : ImageAnalysis.Analyzer {
    private val reader = MultiFormatReader()

    init {
        val hints = mapOf(
            DecodeHintType.POSSIBLE_FORMATS to arrayListOf(BarcodeFormat.QR_CODE)
        )
        reader.setHints(hints)
    }

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null && imageProxy.format == ImageFormat.YUV_420_888) {
            Log.d("BarcodeImageAnalyzer", "Image format and mediaImage are valid.")
            val bitmap = convertYUV420888ToBitmap(mediaImage)
            if (bitmap != null) {
                Log.d("BarcodeImageAnalyzer", "Bitmap conversion successful. Width: ${bitmap.width}, Height: ${bitmap.height}")

                // 이미지 크기 조정
                val scaledBitmap = Bitmap.createScaledBitmap(bitmap, 1024, 1024, false)
                val grayBitmap = toGrayscale(scaledBitmap)

                val intArray = IntArray(grayBitmap.width * grayBitmap.height)
                grayBitmap.getPixels(intArray, 0, grayBitmap.width, 0, 0, grayBitmap.width, grayBitmap.height)
                val source = RGBLuminanceSource(grayBitmap.width, grayBitmap.height, intArray)
                val binaryBitmap = BinaryBitmap(HybridBinarizer(source))
                try {
                    val result = reader.decode(binaryBitmap)
                    Log.d("BarcodeImageAnalyzer", "QR Code detected: ${result.text}")
                    onBarcodeDetected(result.text)
                } catch (e: NotFoundException) {
                    Log.d("BarcodeImageAnalyzer", "No QR code found")
                } finally {
                    imageProxy.close()
                }
            } else {
                Log.e("BarcodeImageAnalyzer", "Bitmap conversion failed.")
                imageProxy.close()
            }
        } else {
            Log.e("BarcodeImageAnalyzer", "Invalid image format or null mediaImage.")
            imageProxy.close()
        }
    }

    private fun convertYUV420888ToBitmap(image: Image): Bitmap? {
        return try {
            val nv21 = yuv420888ToNv21(image)
            val yuvImage = YuvImage(nv21, ImageFormat.NV21, image.width, image.height, null)
            val out = ByteArrayOutputStream()
            yuvImage.compressToJpeg(Rect(0, 0, image.width, image.height), 100, out)
            val yuvByteArray = out.toByteArray()
            BitmapFactory.decodeByteArray(yuvByteArray, 0, yuvByteArray.size)
        } catch (e: Exception) {
            Log.e("BarcodeImageAnalyzer", "YUV to Bitmap conversion error: ${e.message}")
            null
        }
    }

    private fun yuv420888ToNv21(image: Image): ByteArray {
        val yBuffer = image.planes[0].buffer
        val uBuffer = image.planes[1].buffer
        val vBuffer = image.planes[2].buffer

        val ySize = yBuffer.remaining()
        val uSize = uBuffer.remaining()
        val vSize = vBuffer.remaining()

        val nv21 = ByteArray(ySize + uSize + vSize)

        yBuffer.get(nv21, 0, ySize)
        vBuffer.get(nv21, ySize, vSize)
        uBuffer.get(nv21, ySize + vSize, uSize)

        return nv21
    }

    private fun toGrayscale(bitmap: Bitmap): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val grayBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(grayBitmap)
        val paint = Paint()
        val colorMatrix = ColorMatrix()
        colorMatrix.setSaturation(0f)
        val filter = ColorMatrixColorFilter(colorMatrix)
        paint.colorFilter = filter
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        return grayBitmap
    }
}
