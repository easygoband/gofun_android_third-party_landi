package com.gofun.itos_service.printer

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.itos.sdk.cm5.deviceLibrary.Printer.Align
import com.google.zxing.common.BitMatrix

data class PrintableQRItem (
    val value: String,
    val align: Align = Align.LEFT
): PrintableItem{
    fun createBitmapQRFromString(): Bitmap {
        val width = 300
        val height = 300
        val bitMatrix: BitMatrix = MultiFormatWriter().encode(value, BarcodeFormat.QR_CODE, width, height)

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }
        return bitmap
    }
}