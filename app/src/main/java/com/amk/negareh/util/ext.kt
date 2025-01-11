package com.amk.negareh.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import com.amk.negareh.model.data.PhotoGeneratorDallEResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import java.util.Calendar


fun getCurrentTime(): String {
    val c = Calendar.getInstance()
    val hour = c.get(Calendar.HOUR_OF_DAY)

    return when (hour) {
        in 0..5 -> {
            "!بامداد بخیر"
        }

        in 5..12 -> {
            "!صبحت بخیر"
        }

        in 12..15 -> {
            "!ظهرت بخیر"
        }

        in 15..19 -> {
            "!عصرت بخیر"
        }

        in 19..23 -> {
            "!شبت بخیر"
        }

        else -> {
            "!وقتت بخیر"
        }
    }
}

val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    Log.e("Error", "error -> ${throwable.message}")
}

val PGDEX = PhotoGeneratorDallEResponse(0, listOf())

fun sendEmail(context: Context, email: String) {
    val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null))
    startActivity(context, Intent.createChooser(emailIntent, "Send Email..."), null)
}
