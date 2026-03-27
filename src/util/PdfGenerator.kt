package util

import com.lowagie.text.*
import com.lowagie.text.pdf.*
import model.Factura
import java.awt.Color
import java.io.File
import java.io.FileOutputStream
import java.time.format.DateTimeFormatter

class PdfGenerator {

    fun generarPDF(factura: Factura) {

        val carpeta = File("facturas")
        if (!carpeta.exists()) carpeta.mkdirs()

        val formato = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm")
        val fechaFormateada = factura.fecha.format(formato)

        val ruta = "facturas/factura_${factura.numeroFactura}_$fechaFormateada.pdf"

        val document = Document(PageSize.A4, 36f, 36f, 50f, 36f)
        PdfWriter.getInstance(document, FileOutputStream(ruta))

        document.open()

        val azul = Color(33, 64, 154)

        val tituloFont = Font(Font.HELVETICA, 18f, Font.BOLD, azul)
        val normalFont = Font(Font.HELVETICA, 10f, Font.NORMAL, Color.BLACK)
        val boldFont = Font(Font.HELVETICA, 10f, Font.BOLD, Color.BLACK)

        // ======================
        // ENCABEZADO
        // ======================
        val header = PdfPTable(2)
        header.widthPercentage = 100f
        header.setWidths(floatArrayOf(70f, 30f))

        val empresa = PdfPCell()
        empresa.border = Rectangle.NO_BORDER
        empresa.addElement(Paragraph("MI EMPRESA", tituloFont))
        empresa.addElement(Paragraph("Sistema de facturación", normalFont))

        val facturaInfo = PdfPCell()
        facturaInfo.border = Rectangle.NO_BORDER
        facturaInfo.horizontalAlignment = Element.ALIGN_RIGHT
        facturaInfo.addElement(Paragraph("FACTURA", tituloFont))
        facturaInfo.addElement(Paragraph("No. ${factura.numeroFactura}", boldFont))

        header.addCell(empresa)
        header.addCell(facturaInfo)

        document.add(header)
        document.add(Paragraph(" "))

        // ======================
        // DATOS
        // ======================
        val info = PdfPTable(2)
        info.widthPercentage = 100f

        info.addCell(crearCelda("Cliente: ${factura.cliente.nombre}", boldFont))
        info.addCell(crearCelda("Fecha: ${factura.fecha}", normalFont))

        info.addCell(crearCelda("Correo: ${factura.cliente.correo}", normalFont))
        info.addCell(crearCelda("Factura #: ${factura.numeroFactura}", normalFont))

        document.add(info)
        document.add(Paragraph(" "))

        // ======================
        // TABLA PRODUCTOS
        // ======================
        val tabla = PdfPTable(5)
        tabla.widthPercentage = 100f
        tabla.setWidths(floatArrayOf(25f, 35f, 10f, 15f, 15f))

        agregarHeader(tabla, "Producto")
        agregarHeader(tabla, "Descripción")
        agregarHeader(tabla, "Cant.")
        agregarHeader(tabla, "Precio")
        agregarHeader(tabla, "Subtotal")

        for (producto in factura.productos) {
            val subtotalProducto = producto.precio * producto.cantidad

            tabla.addCell(producto.nombre)
            tabla.addCell(producto.descripcion ?: "-")
            tabla.addCell(producto.cantidad.toString())
            tabla.addCell("$${"%.2f".format(producto.precio)}")
            tabla.addCell("$${"%.2f".format(subtotalProducto)}")
        }

        document.add(tabla)
        document.add(Paragraph(" "))

        // ======================
        // TOTALES
        // ======================
        val totales = PdfPTable(2)
        totales.widthPercentage = 40f
        totales.horizontalAlignment = Element.ALIGN_RIGHT

        totales.addCell(crearCelda("Subtotal", boldFont))
        totales.addCell(crearCelda("$${"%.2f".format(factura.subtotal)}", normalFont, Element.ALIGN_RIGHT))

        totales.addCell(crearCelda("IVA (13%)", boldFont))
        totales.addCell(crearCelda("$${"%.2f".format(factura.impuestos)}", normalFont, Element.ALIGN_RIGHT))

        totales.addCell(crearCelda("TOTAL", boldFont))
        totales.addCell(crearCelda("$${"%.2f".format(factura.total)}", boldFont, Element.ALIGN_RIGHT))

        document.add(totales)

        document.add(Paragraph(" "))
        document.add(Paragraph("Gracias por su compra.", normalFont))

        document.close()

        println("Factura generada en: $ruta")
    }

    private fun crearCelda(texto: String, font: Font, align: Int = Element.ALIGN_LEFT): PdfPCell {
        val cell = PdfPCell(Phrase(texto, font))
        cell.horizontalAlignment = align
        cell.setPadding(6f)
        return cell
    }

    private fun agregarHeader(tabla: PdfPTable, texto: String) {
        val cell = PdfPCell(Phrase(texto, Font(Font.HELVETICA, 10f, Font.BOLD, Color.WHITE)))
        cell.backgroundColor = Color(33, 64, 154)
        cell.horizontalAlignment = Element.ALIGN_CENTER
        cell.setPadding(6f)
        tabla.addCell(cell)
    }
}