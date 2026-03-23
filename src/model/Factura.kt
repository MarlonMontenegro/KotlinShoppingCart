package model

import java.time.LocalDateTime

data class Factura(
    val cliente: Cliente,
    val productos: MutableList<Producto> = mutableListOf(),
    val fecha: LocalDateTime = LocalDateTime.now()
) {
    val numeroFactura: Int

    val subtotal: Double
        get() = productos.sumOf { it.precio * it.cantidad }

    val impuestos: Double
        get() = subtotal * IVA

    val total: Double
        get() = subtotal + impuestos

    init {
        numeroFactura = generarNumeroFactura()
    }

    fun agregarProducto(producto: Producto) {
        productos.add(producto)
    }

    companion object {
        private var contador = 1
        private const val IVA = 0.13

        private fun generarNumeroFactura(): Int {
            return contador++
        }
    }
}