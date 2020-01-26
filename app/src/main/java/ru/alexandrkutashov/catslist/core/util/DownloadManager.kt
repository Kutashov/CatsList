package ru.alexandrkutashov.catslist.core.util

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.DownloadManager
import android.app.DownloadManager.Request
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import android.os.Environment


/**
 * @author Alexandr Kutashov
 * on 26.01.2020
 */
class DownloadHelper constructor(private val context: Context) {

    private val downloadManager: DownloadManager =
        context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager

    fun download(url: String) : Boolean {

        if (!isStoragePermissionGranted()) return false

        val uri = Uri.parse(url)
        val request = Request(uri)
        request.setNotificationVisibility(Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, uri.lastPathSegment)
        request.allowScanningByMediaScanner()
        downloadManager.enqueue(request)
        return true
    }

    private fun isStoragePermissionGranted() =
        PermissionHelper.checkPermission(
            context,
            WRITE_EXTERNAL_STORAGE
        )
}