package com.gofun.landi_service

import android.annotation.SuppressLint
import android.app.Application
import android.widget.Toast
import com.sdksuite.corebridge.CoreBridge
import com.sdksuite.corebridge.CoreBridgeConnection
import com.sdksuite.omnidriver.OmniConnection
import com.sdksuite.omnidriver.OmniDriver

class LandiApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
        coreBridge = CoreBridge.me(this)
        omniDriver = OmniDriver.me(this)
        initCoreBridge()
        initOmniDriver()
    }

    private fun initCoreBridge() {
        getCoreBridge().init(object : CoreBridgeConnection {
            override fun onConnected() {
                Toast.makeText(context,"init CoreBridge success", Toast.LENGTH_LONG).show()
            }

            override fun onDisconnected(error: Int) {
                Toast.makeText(context,"Please confirm that the CoreBridge service has been installed on your system!", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun initOmniDriver() {
        getOmniDriver().init(object : OmniConnection {
            override fun onConnected() {
                Toast.makeText(context,"init OmniDriver success", Toast.LENGTH_LONG).show()
            }

            override fun onDisconnected(error: Int) {
                Toast.makeText(context,"Please confirm that the OmniDrive service has been installed on your system!", Toast.LENGTH_LONG).show()
            }

        })
    }

    companion object {
        lateinit var context: LandiApplication

        @SuppressLint("StaticFieldLeak")
        private lateinit var coreBridge: CoreBridge
        private lateinit var omniDriver: OmniDriver

        fun getCoreBridge() = coreBridge
        fun getOmniDriver() = omniDriver
    }
}