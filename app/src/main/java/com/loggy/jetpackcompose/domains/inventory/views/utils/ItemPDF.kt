package com.loggy.jetpackcompose.domains.inventory.views.utils

import android.graphics.Bitmap
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

import java.io.ByteArrayOutputStream
import com.itextpdf.text.Image


fun createPDF(products: List<Product>, fileName: String, signatureBitmap: Bitmap) {
    val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath + "/LoggyInventories"
    val dir = File(path)
    if (!dir.exists()) {
        dir.mkdirs()
    }
    val file = File(dir, "$fileName.pdf")
    val fileOutputStream = FileOutputStream(file)
    val document = Document()

    val writer = PdfWriter.getInstance(document, fileOutputStream)

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
    val table = PdfPTable(4)
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

    // Convertir el Bitmap de la firma en una Image de iTextPdf
    val stream = ByteArrayOutputStream()
    signatureBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
    val signatureImage = Image.getInstance(stream.toByteArray())

    // Ajustar el tamaño de la imagen
    signatureImage.scaleToFit(125f, 50f)

    // Crear una tabla de una sola celda para contener la firma, la línea y el texto
    val signatureTable = PdfPTable(1)

    // Agregar la imagen de la firma a la tabla
    signatureTable.addCell(signatureImage)



    // Agregar el texto "Firma del Operario" debajo de la línea
    val signatureText = Paragraph("Firma del Operario", FontFactory.getFont(FontFactory.HELVETICA, 8f, BaseColor.BLACK))
    signatureText.alignment = Paragraph.ALIGN_CENTER
    signatureTable.addCell(signatureText)

    // Posicionar la tabla en la esquina inferior derecha
    signatureTable.totalWidth = 100f
    signatureTable.writeSelectedRows(0, -1, 40f, signatureTable.totalHeight + 20f, writer.directContent)

    document.close()
}

