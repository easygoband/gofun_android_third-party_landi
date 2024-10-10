package com.gofun.landi_service.printer.printableItems

interface PrintableItem {
    val type: Type
    enum class Type { TEXT, QR, IMAGE}
    enum class Align {LEFT, CENTER, RIGHT}
}