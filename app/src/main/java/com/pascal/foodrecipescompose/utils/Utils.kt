package com.pascal.foodrecipescompose.utils

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

fun generateRandomChar(): Char {
    return ('a'..'z').random()
}

fun intentActionView(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    ContextCompat.startActivity(context, intent, null)
}

fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )
    return image
}

fun Uri.toFile(context: Context): File? {
    return try {
        val contentResolver = context.contentResolver
        val fileName = "${System.currentTimeMillis()}." + getFileExtension(contentResolver)

        val file = File(context.cacheDir, fileName)

        contentResolver.openInputStream(this)?.use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
        }

        file
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

@Throws(IOException::class)
private fun Uri.getFileExtension(contentResolver: ContentResolver): String {
    var extension: String? = null

    if (ContentResolver.SCHEME_CONTENT == scheme && contentResolver.getType(this)?.startsWith("image/") == true) {
        val cursor: Cursor? = contentResolver.query(this, arrayOf(MediaStore.Images.ImageColumns.MIME_TYPE), null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(it.getString(0))
            }
        }
    }

    if (extension == null) {
        extension = MimeTypeMap.getFileExtensionFromUrl(toString())
    }

    if (extension.isNullOrEmpty()) {
        extension = "jpg"
    }

    return extension!!
}