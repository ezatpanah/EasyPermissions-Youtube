package com.ezatpanah.easypermissions_youtube

import android.Manifest.permission.*
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ezatpanah.easypermissions_youtube.databinding.ActivityMainBinding
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog


class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    lateinit var binding: ActivityMainBinding

    companion object {
        const val PERMISSION_CAMERA_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.apply {
            if (hasPermissions()){
                btnRequest.isEnabled = false
                tvMsg.text = getString(R.string.granted_msg)
            }else{
                btnRequest.isEnabled = true
                tvMsg.text = getString(R.string.not_granted_msg)
            }

            btnRequest.setOnClickListener {
                requestPermissions()
            }
        }
    }

    private fun hasPermissions() =
        EasyPermissions.hasPermissions(
            this,
            CAMERA,
            READ_CONTACTS
        )

    private fun requestPermissions() {
        EasyPermissions.requestPermissions(
            this,
            getString(R.string.request_msg),
            PERMISSION_CAMERA_REQUEST_CODE,
            CAMERA, READ_CONTACTS
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            SettingsDialog.Builder(this).build().show()
        } else {
            requestPermissions()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        binding.apply {
            tvMsg.text = getString(R.string.granted_msg)
            btnRequest.isEnabled = false
        }
    }

}