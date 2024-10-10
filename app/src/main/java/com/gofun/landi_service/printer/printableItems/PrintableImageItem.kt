package com.gofun.landi_service.printer.printableItems

import com.fasterxml.jackson.annotation.JsonProperty

data class PrintableImageItem (
    @JsonProperty("base64") val base64: String,
    @JsonProperty("align") val align: PrintableItem.Align,
    @JsonProperty("type") override val type: PrintableItem.Type = PrintableItem.Type.IMAGE
): PrintableItem