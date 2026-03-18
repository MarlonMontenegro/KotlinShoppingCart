package service

import model.Cliente
import model.Factura
import model.Producto

class FacturaService {

    private val facturas = mutableListOf<Factura>()

    fun crearFactura(cliente: Cliente): Factura {
        val factura = Factura(cliente)
        facturas.add(factura)
        return factura
    }

    fun agregarProducto(factura: Factura, producto: Producto) {
        factura.agregarProducto(producto)
    }

    fun obtenerFacturas(): List<Factura> = facturas

    fun buscarFactura(numero: Int): Factura? {
        return facturas.find { it.numeroFactura == numero }
    }
}