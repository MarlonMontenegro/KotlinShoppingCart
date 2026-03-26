package util

import model.Producto
import service.Inventario

class DataSeeder {

    fun cargarProductos(inventario: Inventario) {

        inventario.agregarProductoDirecto(
            Producto("Laptop", "Laptop HP", 850.0, 10)
        )

        inventario.agregarProductoDirecto(
            Producto("Mouse", "Mouse Logitech", 25.0, 50)
        )

        inventario.agregarProductoDirecto(
            Producto("Teclado", "Teclado mecánico", 60.0, 30)
        )

        inventario.agregarProductoDirecto(
            Producto("Monitor", "Monitor 24 pulgadas", 180.0, 15)
        )

        println("Productos cargados correctamente.\n")
    }
}