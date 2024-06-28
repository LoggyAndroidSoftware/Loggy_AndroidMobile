package com.loggy.jetpackcompose.utils.permissions

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun checkPermission(context: Context): Boolean {
    val permission1 =
        ContextCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE)
    val permission2 =
        ContextCompat.checkSelfPermission(context, READ_EXTERNAL_STORAGE)
    return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED
}

fun requestPermissions(context: Context) {
    ActivityCompat.requestPermissions(
        context as Activity,
        arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE),
        200
    )
}