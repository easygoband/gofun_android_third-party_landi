package com.gofun.itos_service.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import com.gofun.itos_service.PrinterInstance
import com.gofun.itos_service.printer.PrintableItem
import com.gofun.itos_service.printer.PrintableQRItem
import com.gofun.itos_service.printer.PrintableTextItem
import com.itos.sdk.cm5.deviceLibrary.Printer.Align

class PrinterService: Service() {

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.i(PrinterService::class.java.name, "ITOS - INIT PRINT")
        val toPrint = intent.getStringExtra("DATA_TO_PRINT")!!
        Log.i(PrinterService::class.java.name, "ITOS - DATA " + toPrint)
        val dataToPrint = try {
            convertDataToPrintableItmes(toPrint)
        } catch (e: Throwable){
            e.printStackTrace()
            Log.i(PrinterService::class.java.name, "ITOS - DATA CONVERSION ERROR")
            throw IllegalArgumentException("Can not convert data to printable object")
        }
        try {
            PrinterInstance.get().init()
            return PrinterInstance.get().print(dataToPrint)
        } catch (e: Throwable){
            e.printStackTrace()
            Log.i(PrinterService::class.java.name, "ITOS - PRINT ERROR " + e.message )
            throw UnsupportedOperationException("Printing error")
        }
    }

    private fun convertDataToPrintableItmes(dataToPrint: String): List<PrintableItem>{
        val jsonNodeResponse = ObjectMapper().readTree(dataToPrint)

        val printableItems = mutableListOf<PrintableItem>()
        for (printableItem in jsonNodeResponse.asIterable()) {
            val type = printableItem.get("type").asText()
            val value = printableItem.get("value").asText()
            val align = Align.valueOf(printableItem.get("align").asText())
            when (type) {
                "TEXT" -> {
                    val fontSize = PrintableTextItem.FontSize.valueOf(printableItem.get("fontSize").asText())
                    val isBold = printableItem.get("bold").asBoolean()
                    val isUnderLine = printableItem.get("underline").asBoolean()
                    val marginStart = printableItem.get("marginStart").asInt()
                    printableItems.add(PrintableTextItem(value, align, fontSize, isBold, isUnderLine, marginStart))
                }
                "QR" -> {
                    printableItems.add(PrintableQRItem(value, align))
                }
            }
        }
        return printableItems
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}