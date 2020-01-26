package ru.alexandrkutashov.catslist.core.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import timber.log.Timber

/**
 * @author Alexandr Kutashov
 * on 26.01.2020
 */
object PermissionHelper {

    val PERMISSION_REQUEST_CODE = 1234

    fun checkPermission(context: Context, permission: String) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                Timber.d("Permission is granted")
                true
            } else {
                Timber.d("Permission is revoked")
                false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Timber.d("Permission is granted")
            true
        }

    fun requestPermission(activity: Activity, permission: String) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(permission),
            PERMISSION_REQUEST_CODE
        )
    }
}