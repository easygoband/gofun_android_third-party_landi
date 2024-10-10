package com.gofun.landi_service.printer
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import com.gofun.landi_service.printer.printableItems.PrintableItem
import com.gofun.landi_service.LandiApplication
import com.sdksuite.omnidriver.aidl.printer.*
import com.sdksuite.omnidriver.api.*
import com.sdksuite.omnidriver.api.OnPrintListener
import com.sdksuite.omnidriver.api.Printer
import java.io.ByteArrayOutputStream

class Printer(private val context: Context) {
    companion object {
        private const val MAX_WIDTH = 384
    }

    private var printer: Printer? = null

    init {
        try {
            printer = LandiApplication.getOmniDriver().getPrinter(Bundle())
        } catch (e: OmniDriverException) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun startPrinter(printableItems: List<PrintableItem>) {
        try {
            printer?.openDevice(0)
            val status = printer?.getStatus().also {
                Toast.makeText(context, "getStatus ret: $it", Toast.LENGTH_SHORT).show()
            }
            if (status != Error.PRINTER_NONE) {
                printer?.closeDevice()
                return
            }
            printer?.setGray(6)
            val bitmap = Utils(context).printableItemsToBitmap(printableItems)
            val br = bmpToByteArray(scaleBitmapProportionally(bitmap))
            printer?.addImage(br, align = Align.CENTER)
            printer?.feedLine(4)
            printer?.startPrint(object : OnPrintListener {
                override fun onSuccess() {
                    Toast.makeText(context, "startPrint success", Toast.LENGTH_SHORT).show()
                }

                override fun onFail(error: Int) {
                    Toast.makeText(context, "startPrint error, ret: $error", Toast.LENGTH_SHORT).show()
                }
            })
        } catch (e: PrinterException) {
            Toast.makeText(context, "Failed to print: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun scaleBitmapProportionally(bitmap: Bitmap): Bitmap {
        val originalWidth = bitmap.width
        val originalHeight = bitmap.height
        val aspectRatio = originalHeight.toFloat() / originalWidth.toFloat()
        val newHeight = (MAX_WIDTH * aspectRatio).toInt()
        return Bitmap.createScaledBitmap(bitmap, MAX_WIDTH, newHeight, true)
    }

    private fun bmpToByteArray(bmp: Bitmap): ByteArray {
        val output = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output)
        val result = output.toByteArray()
        try {
            output.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

}
