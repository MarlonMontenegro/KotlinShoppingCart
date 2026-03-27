package ui

import model.Cliente
import service.Carrito
import service.EmailService
import service.FacturaService
import service.Inventario
import util.PdfGenerator
import util.DataSeeder

class ConsoleUI {

    private val facturaService = FacturaService()
    private var clienteActual: Cliente? = null
    private val inventario: Inventario = Inventario()
    private val carrito = Carrito()
    private val pdfGenerator = PdfGenerator()
    private val emailService = EmailService()
    private val dataSeeder = DataSeeder()

    fun iniciar() {

        dataSeeder.cargarProductos(inventario)
        mostrarBienvenida()
        registrarCliente()

        var salir = false

        while (!salir) {

            println()
            mostrarMenuPrincipal()

            print("Seleccione una opción: ")
            val opcion = readLine()?.toIntOrNull() ?: 0

            when (opcion) {
                1 -> {
                    println("\n--- Productos Disponibles ---")
                    inventario.mostrarProductos()
                }
                2 -> {
                    mostrarMenuCarrito()
                }
                3 -> {
                    finalizarCompra()
                }
                4 -> {
                    println("\nSaliendo del sistema...")
                    salir = true
                }
                else -> println("\nOpción inválida.")
            }
        }
    }

    fun mostrarBienvenida() {
        println("""
        ========================================
                BIENVENIDO A TIENDA XYZ
        ========================================
        """.trimIndent())
    }

    fun mostrarMenuPrincipal() {
        println("""
        ========================================
                CARRITO DE COMPRAS
        ----------------------------------------
        1. Ver productos
        2. Carrito
        3. Finalizar compra
        4. Salir
        ========================================
        """.trimIndent())
    }

    fun registrarCliente() {
        println("\n--- Registro de Cliente ---")

        print("Nombre: ")
        val nombre = readLine() ?: ""

        print("Correo: ")
        val correo = readLine() ?: ""

        clienteActual = Cliente(nombre, correo)

        println("Bienvenido/a, $nombre")
    }

    fun mostrarMenuCarrito() {

        var volver = false

        while (!volver) {
            println("""
            -------- CARRITO --------
            1. Ver carrito
            2. Agregar producto
            3. Eliminar producto
            4. Volver
            ------------------------
            """.trimIndent())

            print("Seleccione: ")
            val opcion = readLine()?.toIntOrNull() ?: 0

            when (opcion) {
                1 -> carrito.mostrarCarrito()
                2 -> agregarProductoAlCarrito()
                3 -> eliminarProductoDelCarrito()
                4 -> volver = true
                else -> println("Opción inválida")
            }
        }
    }

    fun agregarProductoAlCarrito() {

        println("\n--- Productos Disponibles ---")
        inventario.mostrarProductos()

        print("Ingrese el ID del producto: ")
        val id = readLine()?.toIntOrNull()

        if (id == null) {
            println("ID inválido.")
            return
        }

        val producto = inventario.obtenerProductoPorId(id)

        if (producto == null) {
            println("Producto no encontrado.")
            return
        }

        print("Ingrese la cantidad: ")
        val cantidad = readLine()?.toIntOrNull()

        if (cantidad == null || cantidad <= 0) {
            println("Cantidad inválida.")
            return
        }

        if (cantidad > producto.cantidad) {
            println("Stock insuficiente. Disponible: ${producto.cantidad}")
            return
        }

        carrito.agregarProducto(producto, cantidad)
    }

    fun eliminarProductoDelCarrito() {
        print("Ingrese el nombre del producto a eliminar: ")
        val nombre = readLine() ?: ""

        carrito.eliminarProducto(nombre)
    }

    fun finalizarCompra() {

        val cliente = clienteActual

        if (cliente == null) {
            println("No hay cliente registrado.")
            return
        }

        val productos = carrito.obtenerProductos()

        if (productos.isEmpty()) {
            println("El carrito está vacío.")
            return
        }

        println("\n--- RESUMEN DE COMPRA ---")
        carrito.mostrarCarrito()

        print("¿Desea finalizar la compra? (S/N): ")
        val confirmacion = readLine()?.trim()?.uppercase()

        if (confirmacion != "S") {
            println("Compra cancelada.")
            return
        }

        val factura = facturaService.crearFactura(cliente)

        for (producto in productos) {
            facturaService.agregarProducto(factura, producto)
            inventario.reducirStock(producto.nombre, producto.cantidad)
        }

        println("\n--- FACTURA GENERADA ---")
        println("Factura #: ${factura.numeroFactura}")
        println("Cliente: ${factura.cliente.nombre}")
        println("Subtotal: $${"%.2f".format(factura.subtotal)}")
        println("IVA: $${"%.2f".format(factura.impuestos)}")
        println("Total: $${"%.2f".format(factura.total)}")

        pdfGenerator.generarPDF(factura)
        emailService.enviarFacturaPorCorreo(factura)

        carrito.vaciarCarrito()

        println("\nCompra finalizada correctamente.")
    }
}

