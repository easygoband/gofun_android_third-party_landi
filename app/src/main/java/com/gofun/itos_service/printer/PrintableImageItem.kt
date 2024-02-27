package com.gofun.itos_service.printer

import android.graphics.Bitmap
import com.itos.sdk.cm5.deviceLibrary.Printer.Align

data class PrintableImageItem (
    val bitmap: Bitmap,
    val align: Align
): PrintableItem