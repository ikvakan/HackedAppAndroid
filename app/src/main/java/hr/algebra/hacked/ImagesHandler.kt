package hr.algebra.hacked

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Log
import hr.algebra.hacked.factory.createGetHttpUrlConnection
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection

private const val LOGO_WIDTH = 100
private const val LOGO_HEIGHT = 150
private const val QUALITY = 100
private const val TAG = "ImagesHandler"


fun downloadImageAndStore(context: Context,url: String,fileName: String) : String?{
    val extension = url.substring(url.lastIndexOf("."))
    val file: File = getFile(context, fileName, extension)

    try {

        val con: HttpURLConnection = createGetHttpUrlConnection(url)

        con.inputStream.use {`is` ->
            FileOutputStream(file).use { fos->

                val bitmap = BitmapFactory.decodeStream(`is`)

                val resizedBitmap : Bitmap = getResizedBitmap(
                    bitmap,
                    LOGO_WIDTH,
                    LOGO_HEIGHT
                )

                val buffer: ByteArray = getBytesFromBitmap(resizedBitmap)
                fos.write(buffer)
                return file.absolutePath
            }

        }
    } catch (e: Exception) {
        Log.e(TAG, e.message, e)
    }

    return null
}

fun getBytesFromBitmap(bitmap: Bitmap): ByteArray {
    val bos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, QUALITY, bos)
    return bos.toByteArray()
}

fun getResizedBitmap(bitmap: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
    val width = bitmap.width
    val height = bitmap.height
    val scaleWidth = newWidth.toFloat() / width
    val scaleHeight = newHeight.toFloat() / height
    val matrix = Matrix()
    matrix.postScale(scaleWidth, scaleHeight)
    return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false)
}

fun getFile(context: Context, fileName: String, extension: String): File {
    val dir: File? = context.applicationContext.getExternalFilesDir(null)
    val file = File(dir, File.separator.toString() + fileName + extension)
    if (file.exists()) {
        file.delete()
    }
    return file
}