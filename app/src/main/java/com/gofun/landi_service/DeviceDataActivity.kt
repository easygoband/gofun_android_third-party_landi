package com.gofun.landi_service

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sdksuite.corebridge.api.DeviceTag

class DeviceDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val resultIntent = Intent()
        Toast.makeText(this, "GOFUN-LANDI service", Toast.LENGTH_SHORT).show()
        try {
            val deviceTag = intent.getStringExtra("DEVICE_TAG")!!
            val value = LandiApplication.getCoreBridge().getSystemAbility().getDeviceInfo(DeviceTag.valueOf(deviceTag))
            resultIntent.putExtra(deviceTag, value)
            setResult(Activity.RESULT_OK, resultIntent)
        } catch (e: Throwable){
            e.printStackTrace()
            resultIntent.putExtra("ERROR", e.message)
            setResult(Activity.RESULT_CANCELED, resultIntent)
        } finally {
            finish()
        }
    }
}