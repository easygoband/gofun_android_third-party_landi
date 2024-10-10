package com.gofun.landi_service

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.gofun.landi_service.printer.Printer
import com.gofun.landi_service.printer.printableItems.PrintableImageItem
import com.gofun.landi_service.printer.printableItems.PrintableItem
import com.gofun.landi_service.printer.printableItems.PrintableQRItem
import com.gofun.landi_service.printer.printableItems.PrintableTextItem

class PrinterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val resultIntent = Intent()
        Toast.makeText(this, "GOFUN-LANDI service", Toast.LENGTH_SHORT).show()
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
            Printer(this).startPrinter(printableItemsList)
            resultIntent.putExtra("RESULT", "OK")
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