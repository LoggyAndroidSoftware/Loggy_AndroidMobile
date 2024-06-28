package com.loggy.jetpackcompose.domains.inventory.views.utils

import android.os.Environment
import java.io.File

import android.content.Context
import android.print.PrintAttributes
import android.print.PrintManager
import android.print.pdf.PrintedPdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument.Page
import android.os.Bundle
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo

import java.io.FileOutputStream

import com.itextpdf.text.Document
import com.itextpdf.text.Phrase
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import com.loggy.jetpackcompose.domains.inventory.models.Product


fun printPDF(context: Context, filePath: String) {
    val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager
    val printAdapter = object : PrintDocumentAdapter() {
        override fun onLayout(oldAttributes: PrintAttributes?, newAttributes: PrintAttributes?, cancellationSignal: CancellationSignal?, callback: LayoutResultCallback?, extras: Bundle?) {
            if (cancellationSignal?.isCanceled == true) {
                callback?.onLayoutCancelled()
                return
            }
            callback?.onLayoutFinished(PrintDocumentInfo.Builder(filePath).setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT).build(), true)
        }

        override fun onWrite(pages: Array<out PageRange>?, output: ParcelFileDescriptor?, cancellationSignal: CancellationSignal?, callback: WriteResultCallback?) {
            try {
                val pdfDocument = PrintedPdfDocument(context, PrintAttributes.Builder().build())
                val pageInfo = PageInfo.Builder(595, 842, 1).create() // A4 size in points
                val page: Page = pdfDocument.startPage(pageInfo)
                val canvas: Canvas = page.canvas
                val paint = Paint()
                paint.color = Color.BLACK
                paint.textSize = 14f

                val file = File(filePath)
                val lines = file.readLines()
                var y = 25f
                for (line in lines) {
                    canvas.drawText(line, 10f, y, paint)
                    y += paint.descent() - paint.ascent()
                }

                pdfDocument.finishPage(page)
                FileOutputStream(filePath).use { fos ->
                    pdfDocument.writeTo(fos)
                }
                pdfDocument.close()
                callback?.onWriteFinished(arrayOf(PageRange.ALL_PAGES))
            } catch (e: Exception) {
                callback?.onWriteFailed(e.message)
            }
        }
    }
    printManager.print("Document", printAdapter, PrintAttributes.Builder().build())
}

fun createPDF(products: List<Product>, filePath: String) {
    val document = Document()
    PdfWriter.getInstance(document, FileOutputStream(filePath))
    document.open()

    val table = PdfPTable(3) // 3 columns.

    // Add column headers
    table.addCell(PdfPCell(Phrase("Product Name")))
    table.addCell(PdfPCell(Phrase("Brand")))
    table.addCell(PdfPCell(Phrase("Stock")))

    // Add rows
    for (product in products) {
        table.addCell(PdfPCell(Phrase(product.codename)))
        table.addCell(PdfPCell(Phrase(product.brand)))
        table.addCell(PdfPCell(Phrase(product.stock.toString())))
    }

    document.add(table)
    document.close()
}

fun getOutputDirectory(): File {
    val mediaDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
    val fileDir = File(mediaDir, "Loggy")
    if (!fileDir.exists()) {
        fileDir.mkdirs()
    }
    return fileDir
}