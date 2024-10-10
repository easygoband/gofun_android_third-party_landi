package com.gofun.landi_service.printer.printableItems

import com.fasterxml.jackson.annotation.JsonProperty

data class PrintableTextItem (
    @JsonProperty("value") private val value: String,
    @JsonProperty("align") val align: PrintableItem.Align = PrintableItem.Align.LEFT,
    @JsonProperty("fontSize") val fontSize: FontSize = FontSize.REGULAR,
    @JsonProperty("bold") val isBold: Boolean = false,
    @JsonProperty("underline") val isUnderline: Boolean = false,
    @JsonProperty("marginStart") val marginStart: Int = 0,
    @JsonProperty("type") override val type: PrintableItem.Type = PrintableItem.Type.TEXT
): PrintableItem {

    @get:JsonProperty("value")
    val formattedValue: String
        get() {
            return when (fontSize){
                FontSize.BIG -> insertLineBreaks(value, 18)
                FontSize.INFO -> insertLineBreaks(value, 22)
                FontSize.REGULAR -> insertLineBreaks(value, 32)
                FontSize.SMALL -> insertLineBreaks(value, 38)
            }
        }
    enum class FontSize {
        BIG, INFO, REGULAR, SMALL
    }

    private fun insertLineBreaks(input: String, chunkSize: Int): String {
        val stringBuilder = StringBuilder(input)
        var index = chunkSize
        while (index < stringBuilder.length) {
            stringBuilder.insert(index, "\n")
            index += chunkSize + 1 // Sumar 1 para compensar el carácter añadido (\n)
        }
        return stringBuilder.toString()
    }

}