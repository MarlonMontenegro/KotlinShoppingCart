package util

import com.lowagie.text.Document
import com.lowagie.text.Paragraph
import com.lowagie.text.pdf.PdfWriter
import model.Factura
import java.io.File
import java.io.FileOutputStream

class PdfGenerator {

    fun generarPDF(factura: Factura) {
        val carpeta = File("facturas")
        if (!carpeta.exists()) carpeta.mkdirs()

        val ruta = "facturas/factura_${factura.numeroFactura}.pdf"

        val document = Document()
        PdfWriter.getInstance(document, FileOutputStream(ruta))

        document.open()

        document.add(Paragraph("Factura #${factura.numeroFactura}"))
        document.add(Paragraph("Fecha: ${factura.fecha}"))
        document.add(Paragraph("Cliente: ${factura.cliente.nombre}"))
        document.add(Paragraph("Correo: ${factura.cliente.correo}"))
        document.add(Paragraph(" "))

        for (producto in factura.productos) {
            val subtotalProducto = producto.precio * producto.cantidad
            document.add(
                Paragraph(
                    "Producto: ${producto.nombre} | Cantidad: ${producto.cantidad} | Precio: ${producto.precio} | Subtotal: $subtotalProducto"
                )
            )
        }

        document.add(Paragraph(" "))
        document.add(Paragraph("Subtotal: ${factura.subtotal}"))
        document.add(Paragraph("IVA: ${factura.impuestos}"))
        document.add(Paragraph("Total: ${factura.total}"))

        document.close()

        println("Factura generada en: $ruta")
    }
}