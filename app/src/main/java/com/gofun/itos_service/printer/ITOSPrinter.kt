package com.gofun.itos_service.printer

import android.content.Context
import com.itos.sdk.cm5.deviceLibrary.IDevice
import com.itos.sdk.cm5.deviceLibrary.Printer.*

class ITOSPrinter(val context: Context) {
    private lateinit var itosPrinter: Printer

    fun init() {
        this.itosPrinter = Printer(context)
        IDevice(context)
    }

    fun print(toPrintItems: List<PrintableItem>): Int{
        itosPrinter.initPrinter()
        toPrintItems.forEach {
            when (it) {
                is PrintableTextItem -> appendTextToPrint(it)
                is PrintableImageItem -> appendImageToPrint(it)
                is PrintableQRItem -> appendQRToPrint(it)
            }
        }
        itosPrinter.appendStr(
            " ",
            FontEntity(DotMatrixFontEnum.Serif_20X32, DotMatrixFontEnum.Serif_20X32),
            Align.CENTER
        )
        return itosPrinter.startPrint(true) {}
    }

    private fun appendTextToPrint(
        printableTextItem: PrintableTextItem
    ) {
        val textSize = when (printableTextItem.fontSize){
            PrintableTextItem.FontSize.BIG -> FontEntity(DotMatrixFontEnum.Serif_20X32, DotMatrixFontEnum.Serif_20X32)
            PrintableTextItem.FontSize.REGULAR -> FontEntity(DotMatrixFontEnum.Serif_12X24, DotMatrixFontEnum.Serif_12X24)
            PrintableTextItem.FontSize.SMALL -> FontEntity(DotMatrixFontEnum.Serif_8X16, DotMatrixFontEnum.Serif_8X16)
        }

        val line = LineOptionEntity()
        line.isBold = printableTextItem.isBold
        line.isUnderline = printableTextItem.isUnderline
        line.marginLeft = printableTextItem.marginStart

        itosPrinter.appendStr(printableTextItem.value, textSize, printableTextItem.align, line)
    }

    private fun appendImageToPrint(printableImageItem: PrintableImageItem) {
        itosPrinter.appendImage(printableImageItem.bitmap, printableImageItem.align)
    }

    private fun appendQRToPrint(printableQRItem: PrintableQRItem) {
        itosPrinter.appendImage(printableQRItem.createBitmapQRFromString(), printableQRItem.align)
    }
}