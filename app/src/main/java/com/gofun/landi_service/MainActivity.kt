package com.gofun.landi_service

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gofun.landi_service.databinding.ActivityMainBinding
import com.sdksuite.corebridge.api.DeviceTag

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.content.getSystemImei.setOnClickListener {
            binding.content.systemImei.visibility = View.VISIBLE
            binding.content.getSystemImei.visibility = View.GONE
            val imei = LandiApplication.getCoreBridge().getSystemAbility().getDeviceInfo(DeviceTag.IMEI)
            binding.content.systemImei.setText("${getString(R.string.system_imei)} $imei")
        }
    }

}