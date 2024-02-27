package com.gofun.itos_service

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gofun.itos_service.databinding.ActivityMainBinding
import com.gofun.itos_service.printer.PrintableImageItem
import com.gofun.itos_service.printer.PrintableQRItem
import com.gofun.itos_service.printer.PrintableTextItem
import com.itos.sdk.cm5.deviceLibrary.Printer.Align

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        PrinterInstance.set(context = this.application.applicationContext)
        binding.content.printTest.setOnClickListener {
            PrinterInstance.get().init()
            val toPrint = listOf(
                PrintableTextItem(
                    value = "PRINTING TEST",
                    align = Align.CENTER,
                    fontSize = PrintableTextItem.FontSize.BIG,
                    isBold = true,
                    isUnderline = false,
                    marginStart = 0
                ),
                PrintableTextItem(
                    value = "This is a test to verify that the ITOS printer is working correctly.",
                    align = Align.LEFT,
                    fontSize = PrintableTextItem.FontSize.REGULAR,
                    isBold = false,
                    isUnderline = false,
                    marginStart = 0
                ),
                PrintableImageItem(
                    bitmap = drawableToBitmap(this.application.applicationContext.getDrawable(R.drawable.gofun_logo)!!),
                    align = Align.CENTER
                ),
                PrintableQRItem(
                    "https://gofun.easygoband.com/",
                    Align.CENTER
                ),
                PrintableTextItem(
                    value = "That seems to be the case :)",
                    align = Align.RIGHT,
                    fontSize = PrintableTextItem.FontSize.SMALL,
                    isBold = false,
                    isUnderline = true,
                    marginStart = 0
                ),
            )
            PrinterInstance.get().print(toPrint)
        }
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }
}