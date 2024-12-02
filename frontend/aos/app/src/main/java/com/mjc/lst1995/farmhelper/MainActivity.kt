package com.mjc.lst1995.farmhelper

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val permissions =
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                Manifest.permission.READ_MEDIA_IMAGES,
            )
        } else {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!permissionCheck()) requestPermission()
    }

    private fun permissionCheck(): Boolean = permissions.all { permissionIsPossible(it) }

    private fun requestPermission() {
        // 만약 사용자가 "다시 묻지 않음"을 선택했는지 확인
        if (shouldShowPermissionRationale()) {
            showPermissionDeniedDialogWithSettings()
        } else {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION_CODE)
        }
    }

    // 사용자가 "다시 묻지 않음"을 선택했는지 확인하는 함수
    private fun shouldShowPermissionRationale(): Boolean = permissions.any { ActivityCompat.shouldShowRequestPermissionRationale(this, it) }

    private fun permissionIsPossible(permission: String): Boolean =
        ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            } else {
                // 권한 거부 시 다시 권한 요청 또는 설정으로 이동
                if (shouldShowPermissionRationale()) {
                    showPermissionDeniedDialogWithSettings()
                } else {
                    showPermissionDeniedDialog()
                }
            }
        }
    }

    // 권한이 거부된 경우 설정 화면으로 안내하는 다이얼로그
    private fun showPermissionDeniedDialogWithSettings() {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.permission_required_message))
            .setMessage(resources.getString(R.string.permission_instructions))
            .setPositiveButton(resources.getString(R.string.navigate_to_settings)) { _, _ ->
                // 앱의 설정 화면으로 이동하여 권한을 허용하도록 유도
                val intent =
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = android.net.Uri.fromParts("package", packageName, null)
                    }
                startActivity(intent)
                finish()
            }.setNegativeButton(resources.getString(R.string.back)) { _, _ ->
                finish()
            }.show()
    }

    private fun showPermissionDeniedDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.allow_permission))
            .setMessage(resources.getString(R.string.request_permission_message))
            .setPositiveButton(resources.getString(R.string.check)) { _, _ ->
                requestPermission()
            }.setNegativeButton(resources.getString(R.string.back)) { _, _ ->
                finish()
            }.show()
    }

    companion object {
        private const val REQUEST_PERMISSION_CODE = 200
    }
}
