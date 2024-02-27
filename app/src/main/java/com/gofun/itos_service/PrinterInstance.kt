package com.gofun.itos_service

import android.content.Context
import com.gofun.itos_service.printer.ITOSPrinter

class PrinterInstance {
    companion object {
        private var instance: ITOSPrinter? = null
        fun get(): ITOSPrinter { return instance!! }
        fun set(context: Context){
            if (instance == null) {
                instance = ITOSPrinter(context)
            }
        }
    }
}