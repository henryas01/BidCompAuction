// utils/FileUtil.kt
//package utils
package com.example.bidcompauction.utils

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

object FileUtil {
    fun getFileFromUri(context: Context, uri: Uri): File {
        val contentResolver = context.contentResolver
        val tempFile = File(context.cacheDir, "upload_image_${System.currentTimeMillis()}.jpg")

        contentResolver.openInputStream(uri)?.use { input ->
            FileOutputStream(tempFile).use { output ->
                input.copyTo(output)
            }
        }
        return tempFile
    }
}