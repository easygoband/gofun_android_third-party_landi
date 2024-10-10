package com.gofun.landi_service.printer

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Base64
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.gofun.landi_service.printer.printableItems.PrintableImageItem
import com.gofun.landi_service.printer.printableItems.PrintableItem
import com.gofun.landi_service.printer.printableItems.PrintableQRItem
import com.gofun.landi_service.printer.printableItems.PrintableTextItem
import com.gofun.landi_service.R
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import java.io.ByteArrayOutputStream

class Utils(private val context: Context) {
    fun printableItemsToBitmap(toPrintItems: List<PrintableItem>): Bitmap{
        val linearLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundResource(R.color.white)
            setPadding(0, 0, 0, 0)
            layoutParams = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                setMargins(0, 0, 0, 0)
            }
        }

        toPrintItems.forEach {
            when (it) {
                is PrintableTextItem -> {
                    val textView = TextView(context).apply {
                        text = it.formattedValue.padStart(it.formattedValue.length + it.marginStart, ' ')
                        setTextSize(
                            TypedValue.COMPLEX_UNIT_PX,
                            when (it.fontSize){
                                PrintableTextItem.FontSize.BIG -> 80f
                                PrintableTextItem.FontSize.INFO -> 62f
                                PrintableTextItem.FontSize.REGULAR -> 44f
                                PrintableTextItem.FontSize.SMALL -> 36f
                            }
                        )
                        typeface = Typeface.create(
                            Typeface.MONOSPACE,
                            if (it.isBold) Typeface.BOLD else Typeface.NORMAL
                        )

                        if (it.isUnderline) { paintFlags =  Paint.UNDERLINE_TEXT_FLAG }
                        gravity = when (it.align){
                            PrintableItem.Align.LEFT -> Gravity.LEFT
                            PrintableItem.Align.CENTER -> Gravity.CENTER
                            PrintableItem.Align.RIGHT -> Gravity.RIGHT
                        }
                        setTextColor(Color.BLACK)
                        setBackgroundResource(R.color.white)
                    }
                    linearLayout.addView(textView)
                }
                is PrintableImageItem -> {
                    val imageView = ImageView(context).apply {
                        setImageDrawable(base64ToDrawable(it.base64))
                        setBackgroundResource(R.color.white)
                    }
                    linearLayout.addView(imageView)
                }
                is PrintableQRItem -> {
                    val imageView = ImageView(context).apply {
                        setImageDrawable(base64ToDrawable(getQRInBase64(it.value)))
                        setBackgroundResource(R.color.white)
                    }
                    linearLayout.addView(imageView)
                }
            }
        }

        //to bitmap
        linearLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        linearLayout.layout(0, 0, linearLayout.measuredWidth, linearLayout.measuredHeight)
        val bitmap = Bitmap.createBitmap(linearLayout.width, linearLayout.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        linearLayout.draw(canvas)
        return bitmap
    }

    private fun base64ToDrawable(base64String: String): Drawable {
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        return BitmapDrawable(Resources.getSystem(), bitmap)
    }

    private fun getQRInBase64(value: String): String {
        val width = 600
        val height = 600
        val bitMatrix: BitMatrix = MultiFormatWriter().encode(value, BarcodeFormat.QR_CODE, width, height)

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

}