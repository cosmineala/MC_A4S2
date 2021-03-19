package com.example.mcapp.MyCameraX

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.mcapp.R


class CameraFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (allPermissionsGranted()) {
            //startCamera()
        } else {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it, PERMISSIONS.REQUIRED_PERMISSIONS, PERMISSIONS.REQUEST_CODE_PERMISSIONS)
            }
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_camera, container, false)

        return  view
    }

    companion object PERMISSIONS {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        const val REQUEST_CODE_PERMISSIONS = 10
        val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }


    private fun allPermissionsGranted() = PERMISSIONS.REQUIRED_PERMISSIONS.all {
        activity?.let { activity ->
            ContextCompat.checkSelfPermission(
                activity.baseContext, it)
        } == PackageManager.PERMISSION_GRANTED
    }

}