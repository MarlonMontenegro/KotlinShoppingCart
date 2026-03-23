package ui

import model.Cliente
import service.FacturaService
import service.Inventario

class ConsoleUI {

    private val facturaService = FacturaService()
    private var clienteActual: Cliente? = null
    private val inventario : Inventario = Inventario()

    fun iniciar() {

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
                    println("\n--- Carrito de Compras ---")
                    // verCarrito()
                }
                3 -> {
                    println("\n--- Eliminar Producto del Carrito ---")
                    // eliminarProducto()
                }
                4 -> {
                    println("\n--- Finalizar Compra ---")
                    // finalizarCompra()
                }
                5 -> {
                    println("\nSaliendo del sistema...")
                    salir = true
                }
                else -> println("\nOpción inválida. Intente nuevamente.")
            }
        }
    }


    fun mostrarBienvenida(){
        println("""
        ========================================
                BIENVENIDO A TIENDA XYZ
        ========================================
""".trimIndent())
    }

    fun mostrarMenuPrincipal() {
        println("""
        ========================================
                BIENVENIDO A TIENDA XYZ
        ========================================
                Carritos de Compras
        ----------------------------------------
        1. Ver productos
        2. Ver carrito
        3. Eliminar producto del carrito
        4. Finalizar compra
        5. Salir del sistema
        ----------------------------------------
        Seleccione una opción para continuar:
        ========================================
        """.trimIndent())
    }




    fun registrarCliente() {

        println("\n--- Registro de Cliente ---")

        print("Ingrese su nombre: ")
        val nombre = readLine() ?: ""

        print("Ingrese su correo: ")
        val correo = readLine() ?: ""

        clienteActual = Cliente(nombre, correo)

        println("\nCliente registrado correctamente.")
        println("Bienvenido/a, $nombre")
    }





}