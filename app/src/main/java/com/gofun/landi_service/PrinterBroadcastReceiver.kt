package com.gofun.landi_service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.gofun.landi_service.printer.Printer
import com.gofun.landi_service.printer.printableItems.PrintableImageItem
import com.gofun.landi_service.printer.printableItems.PrintableItem
import com.gofun.landi_service.printer.printableItems.PrintableQRItem
import com.gofun.landi_service.printer.printableItems.PrintableTextItem

class PrinterBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "GOFUN-LANDI service", Toast.LENGTH_SHORT).show()
        try {
            val objectMapper = ObjectMapper()
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

            val printableItemsList = mutableListOf<PrintableItem>()

            val bundle: Bundle? = intent.extras
            if (bundle != null) {
                for (index in 0 until bundle.size()) {
                    val jsonPrintableItem = bundle.getString(index.toString())
                    if (jsonPrintableItem != null) {
                        val node: JsonNode = objectMapper.readTree(jsonPrintableItem)
                        val type = node.get("type").asText()
                        val printableItem = when (type) {
                            "TEXT" -> objectMapper.readValue(jsonPrintableItem, PrintableTextItem::class.java)
                            "QR" -> objectMapper.readValue(jsonPrintableItem, PrintableQRItem::class.java)
                            "IMAGE" -> objectMapper.readValue(jsonPrintableItem, PrintableImageItem::class.java)
                            else -> throw IllegalArgumentException("Unknow printable type: $type")
                        }
                        printableItemsList.add(printableItem)
                    }
                }
            }
            Printer(context).startPrinter(printableItemsList)
        } catch (e: Throwable){
            e.printStackTrace()
        } finally {

        }
    }
}