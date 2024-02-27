package com.gofun.itos_service.printer

import com.itos.sdk.cm5.deviceLibrary.Printer.Align

data class PrintableTextItem (
    val value: String,
    val align: Align = Align.LEFT,
    val fontSize: FontSize = FontSize.REGULAR,
    val isBold: Boolean = false,
    val isUnderline: Boolean = false,
    val marginStart: Int = 0
): PrintableItem {
    enum class FontSize {
        BIG, REGULAR, SMALL
    }
}