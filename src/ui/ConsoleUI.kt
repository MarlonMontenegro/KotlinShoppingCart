package ui

import service.FacturaService

class ConsoleUI {

    private val facturaService = FacturaService()

    fun iniciar() {

        var salir = false

        while (!salir){
            mostrarMenuPrincipal()

            print("Seleccione una opcion: ")
            val opcion = readLine()?.toIntOrNull() ?: 0

            when (opcion) {
                1 -> println("Ir a menu de facturas")
                2 -> println("Ir a menu de inventario")
                3 -> println("Ir a menu de carrito")
                4 -> println("Ir a menu de correo")
                5 -> {
                    println("Saliendo del sistema...")
                    salir = true

                }
                else -> println("Opcion invalida")
            }



        }

    }

    fun mostrarMenuPrincipal() {
        println()

        println("===== SISTEMA DE VENTAS =====")
        println("1. Facturas")
        println("2. Inventario")
        println("3. Carrito de compras")
        println("4. Correo")
        println("5. Salir")
        println()

    }

    fun menuFacturas() {

    }

    fun mostrarFacturas() {
        val listaFacturas = facturaService.listarFacturas()

        if (listaFacturas.isEmpty()) {
            println("No hay facturas registradas.")
            return
        }

        println("===== FACTURAS =====")

        for ((index, registro) in listaFacturas.withIndex()) {
            val factura = registro.first

            println("${index + 1}. Factura #${factura.numero} | Cliente: ${factura.cliente} | Total: $${factura.total}")
        }
    }

}