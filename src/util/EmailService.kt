package service

import jakarta.mail.Authenticator
import jakarta.mail.Message
import jakarta.mail.PasswordAuthentication
import jakarta.mail.Session
import jakarta.mail.Transport
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeBodyPart
import jakarta.mail.internet.MimeMessage
import jakarta.mail.internet.MimeMultipart
import model.Factura
import java.io.File
import java.util.Properties

class EmailService(
    private val smtpHost: String  = "sandbox.smtp.mailtrap.io",
    private val smtpPort: String    =    "2525",
    private val smtpUser: String = "7f3815e0479e5c",
    private val smtpPass: String = "ea93590cf94c71",
    private val remitente: String = "facturas@demo.com"
) {

    private fun crearSesion(): Session {
        val props = Properties().apply {
            put("mail.smtp.host", smtpHost)
            put("mail.smtp.port", smtpPort)
            put("mail.smtp.auth", "true")
            put("mail.smtp.starttls.enable", "true")
        }

        return Session.getInstance(props, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(smtpUser, smtpPass)
            }
        })
    }

    private fun cargarPlantilla(rutaPlantilla: String): String {
        val archivo = File(rutaPlantilla)

        if (!archivo.exists()) {
            throw IllegalArgumentException("No se encontró la plantilla: $rutaPlantilla")
        }

        return archivo.readText(Charsets.UTF_8)
    }

    private fun construirDetalleProductos(factura: Factura): String {
        return factura.productos.joinToString("") { producto ->
            val subtotal = producto.precio * producto.cantidad
            """
            <tr>
                <td>${producto.nombre}</td>
                <td>${producto.cantidad}</td>
                <td>$${"%.2f".format(producto.precio)}</td>
                <td>$${"%.2f".format(subtotal)}</td>
            </tr>
            """.trimIndent()
        }
    }

    private fun procesarPlantilla(html: String, factura: Factura): String {
        val detalleProductos = construirDetalleProductos(factura)

        return html
            .replace("{{cliente}}", factura.cliente.nombre)
            .replace("{{correo}}", factura.cliente.correo)
            .replace("{{factura}}", factura.numeroFactura.toString())
            .replace("{{fecha}}", factura.fecha.toString())
            .replace("{{subtotal}}", "%.2f".format(factura.subtotal))
            .replace("{{iva}}", "%.2f".format(factura.impuestos))
            .replace("{{total}}", "%.2f".format(factura.total))
            .replace("{{detalle_productos}}", detalleProductos)
    }

    fun enviarFacturaPorCorreo(factura: Factura) {
        val rutaPdf = "facturas/factura_${factura.numeroFactura}.pdf"
        val archivoPdf = File(rutaPdf)

        println("Buscando PDF en: ${archivoPdf.absolutePath}")
        println("¿Existe el PDF? ${archivoPdf.exists()}")
        println("Tamaño PDF: ${if (archivoPdf.exists()) archivoPdf.length() else 0}")

        if (!archivoPdf.exists()) {
            throw IllegalArgumentException("No se encontró el PDF: $rutaPdf")
        }

        val plantilla = cargarPlantilla("src/templates/correo.html")
        val htmlFinal = procesarPlantilla(plantilla, factura)

        val session = crearSesion()
        val message = MimeMessage(session)

        message.setFrom(InternetAddress(remitente))
        message.setRecipients(
            Message.RecipientType.TO,
            InternetAddress.parse(factura.cliente.correo)
        )
        message.subject = "Factura #${factura.numeroFactura}"

        val htmlPart = MimeBodyPart().apply {
            setContent(htmlFinal, "text/html; charset=UTF-8")
        }

        val pdfPart = MimeBodyPart().apply {
            dataHandler = jakarta.activation.DataHandler(
                jakarta.activation.FileDataSource(archivoPdf)
            )
            fileName = archivoPdf.name
            disposition = MimeBodyPart.ATTACHMENT
        }

        val multipart = MimeMultipart().apply {
            addBodyPart(htmlPart)
            addBodyPart(pdfPart)
        }

        message.setContent(multipart)
        message.saveChanges()

        Transport.send(message)
        println("Correo enviado correctamente a ${factura.cliente.correo}")
    }
}