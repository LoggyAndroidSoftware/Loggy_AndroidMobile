package com.loggy.jetpackcompose.domains.inventory.views.utils

import android.os.Environment
import java.io.File

import java.io.FileOutputStream


import com.itextpdf.text.BaseColor
import com.itextpdf.text.Document
import com.itextpdf.text.FontFactory
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import com.loggy.jetpackcompose.domains.inventory.models.Product
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun createPDF(products: List<Product>, fileName: String) {
    val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath + "/LoggyInventories"
    val dir = File(path)
    if (!dir.exists()) {
        dir.mkdirs()
    }
    val file = File(dir, "$fileName.pdf")
    val fileOutputStream = FileOutputStream(file)
    val document = Document()

    PdfWriter.getInstance(document, fileOutputStream)

    document.open()
    //Titulo del documento
    val title = Paragraph("Inventario de productos \n\n",
        FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22f, BaseColor.BLACK))
    title.alignment = Paragraph.ALIGN_CENTER
    document.add(title)

    // Subtítulo con la fecha y hora actuales
    val currentDateTime = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(Date())
    val subtitle = Paragraph("Generado el: $currentDateTime \n\n",
        FontFactory.getFont(FontFactory.HELVETICA, 18f, BaseColor.BLACK))
    subtitle.alignment = Paragraph.ALIGN_CENTER
    document.add(subtitle)

    //Tabla de productos
    var table = PdfPTable(4)
    table.addCell("Producto")
    table.addCell("Marca")
    table.addCell("Lote")
    table.addCell("Cantidad")

    for (it in products){
        table.addCell(it.codename)
        table.addCell(it.brand)
        table.addCell(it.batch)
        table.addCell(it.stock.toString())
    }
    document.add(table)
    document.close()

}

