package model

import java.time.LocalDateTime

data class FacturaRegistro(
    val cliente: Cliente,
    val productos: List<Producto>,
    val subtotal: Double,
    val impuestos: Double,
    val total: Double,
    val fecha: LocalDateTime,
    val numeroFactura: Int
)