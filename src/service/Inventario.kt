package service

import model.Producto

class Inventario {

    private val listaProductos = mutableListOf<Producto>()

    // Agregar producto
    fun agregarProducto() {

        println("Ingrese el nombre del producto:")
        val nombre = readLine() ?: ""

        println("Ingrese la descripción:")
        val descripcion = readLine() ?: ""

        println("Ingrese el precio:")
        val precio = readLine()?.toDoubleOrNull() ?: 0.0

        println("Ingrese la cantidad:")
        val cantidad = readLine()?.toIntOrNull() ?: 0

        val nuevoProducto = Producto(nombre, descripcion, precio, cantidad)

        listaProductos.add(nuevoProducto)

        println("model.Producto agregado correctamente.\n")
    }

    // Mostrar productos
    fun mostrarProductos() {

        if (listaProductos.isEmpty()) {
            println("No hay productos en el inventario.\n")
            return
        }

        println("=============== PRODUCTOS ===============")

        listaProductos.forEachIndexed { index, producto ->
            println("ID: $index | ${producto.nombre} | Precio: $${producto.precio} | Stock: ${producto.cantidad}")
        }

        println("=========================================\n")
    }

    // Editar producto
    fun editarProducto() {

        mostrarProductos()

        println("Ingrese el ID del producto que desea editar:")
        val id = readLine()?.toIntOrNull()

        if (id == null || id !in listaProductos.indices) {
            println("model.Producto no encontrado.\n")
            return
        }

        val producto = listaProductos[id]

        println("Nuevo nombre:")
        producto.nombre = readLine() ?: producto.nombre

        println("Nueva descripción:")
        producto.descripcion = readLine() ?: producto.descripcion

        println("Nuevo precio:")
        producto.precio = readLine()?.toDoubleOrNull() ?: producto.precio

        println("Nueva cantidad:")
        producto.cantidad = readLine()?.toIntOrNull() ?: producto.cantidad

        println("model.Producto actualizado correctamente.\n")
    }

    // Eliminar producto
    fun eliminarProducto() {

        mostrarProductos()

        println("Ingrese el ID del producto que desea eliminar:")
        val id = readLine()?.toIntOrNull()

        if (id == null || id !in listaProductos.indices) {
            println("model.Producto no encontrado.\n")
            return
        }

        listaProductos.removeAt(id)

        println("model.Producto eliminado correctamente.\n")
    }

    fun obtenerProductoPorId(id: Int): Producto? {
        if (id !in listaProductos.indices) {
            return null
        }
        return listaProductos[id]
    }

    fun agregarProductoDirecto(producto: Producto) {
        listaProductos.add(producto)
    }

    fun reducirStock(nombre: String, cantidad: Int) {

        val producto = listaProductos.find {
            it.nombre.equals(nombre, ignoreCase = true)
        }

        if (producto != null) {
            producto.cantidad -= cantidad
        }
    }
}