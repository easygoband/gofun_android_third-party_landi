package com.gofun.landi_service

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gofun.landi_service.databinding.ActivityMainBinding
import com.gofun.landi_service.printer.Printer
import com.gofun.landi_service.printer.printableItems.PrintableItem
import com.gofun.landi_service.printer.printableItems.PrintableQRItem
import com.gofun.landi_service.printer.printableItems.PrintableTextItem
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

        binding.content.printTest.setOnClickListener {
            Printer(this).startPrinter(
                listOf(
                    PrintableTextItem(
                        value = "PRINTING TEST",
                        align = PrintableItem.Align.CENTER,
                        fontSize = PrintableTextItem.FontSize.BIG,
                        isBold = true,
                        isUnderline = false,
                        marginStart = 0
                    ),
                    PrintableTextItem(
                        value = "This is a test to verify that the LANDI printer is working correctly.",
                        align = PrintableItem.Align.LEFT,
                        fontSize = PrintableTextItem.FontSize.REGULAR,
                        isBold = false,
                        isUnderline = false,
                        marginStart = 0
                    ),
                    PrintableQRItem(
                        "https://gofun.easygoband.com/",
                        PrintableItem.Align.CENTER
                    ),
                    PrintableTextItem(
                        value = "That seems to be the case :)",
                        align = PrintableItem.Align.RIGHT,
                        fontSize = PrintableTextItem.FontSize.SMALL,
                        isBold = false,
                        isUnderline = true,
                        marginStart = 0
                    )
                )
            )
        }
    }

}