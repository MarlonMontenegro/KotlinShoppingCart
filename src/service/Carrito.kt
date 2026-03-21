package service

import model.Producto

class Carrito {

    private val carrito = mutableListOf<Producto>()

    fun agregarProducto(producto: Producto, cantidad: Int) {
        if (cantidad <= 0) {
            println("❌ Cantidad inválida")
            return
        }

        val existente = carrito.find { it.nombre == producto.nombre }

        if (existente != null) {
            existente.cantidad += cantidad
        } else {
            val nuevoProducto = producto.copy(cantidad = cantidad)
            carrito.add(nuevoProducto)
        }

        println("✅ Producto agregado al carrito")
    }

    fun eliminarProducto(nombre: String) {
        val eliminado = carrito.removeIf {
            it.nombre.equals(nombre, ignoreCase = true)
        }

        if (eliminado) {
            println("🗑 Producto eliminado")
        } else {
            println("❌ Producto no encontrado")
        }
    }

    fun mostrarCarrito() {
        if (carrito.isEmpty()) {
            println("🛒 Carrito vacío")
            return
        }

        println("\n🛒 Carrito:")
        carrito.forEachIndexed { index, producto ->
            val subtotal = producto.precio * producto.cantidad
            println("${index + 1}. ${producto.nombre} | Cantidad: ${producto.cantidad} | Precio: ${producto.precio} | Subtotal: $subtotal")
        }

        println("💰 Total: ${calcularTotal()}")
    }

    fun calcularTotal(): Double {
        return carrito.sumOf { it.precio * it.cantidad }
    }
}