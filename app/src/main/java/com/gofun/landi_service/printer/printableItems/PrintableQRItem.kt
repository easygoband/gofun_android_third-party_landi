package com.gofun.landi_service.printer.printableItems

import com.fasterxml.jackson.annotation.JsonProperty

data class PrintableQRItem (
    @JsonProperty("value") val value: String,
    @JsonProperty("align") val align: PrintableItem.Align = PrintableItem.Align.LEFT,
    @JsonProperty("type") override val type: PrintableItem.Type = PrintableItem.Type.QR
): PrintableItem