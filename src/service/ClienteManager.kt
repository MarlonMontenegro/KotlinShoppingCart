class ClienteManager {

    private val listaClientes = mutableListOf<Cliente>()

    // CREATE - Agregar cliente
    fun agregarCliente() {

        println("Ingrese el nombre del cliente:")
        val nombre = readLine() ?: ""

        println("Ingrese el correo del cliente:")
        val correo = readLine() ?: ""

        val cliente = Cliente(nombre, correo)

        listaClientes.add(cliente)

        println("Cliente agregado correctamente.\n")
    }

    // READ - Mostrar clientes
    fun mostrarClientes() {

        if (listaClientes.isEmpty()) {
            println("No hay clientes registrados.\n")
            return
        }

        println("Lista de clientes:")

        listaClientes.forEachIndexed { index, cliente ->

            println("ID: $index")
            cliente.mostrarCliente()

        }
    }

    // UPDATE - Editar cliente
    fun editarCliente() {

        mostrarClientes()

        println("Ingrese el ID del cliente que desea editar:")
        val id = readLine()?.toIntOrNull()

        if (id == null  id !in listaClientes.indices) {
            println("Cliente no encontrado.\n")
            return
        }

        val cliente = listaClientes[id]

        println("Nuevo nombre:")
        cliente.nombre = readLine() ?: cliente.nombre

        println("Nuevo correo:")
        cliente.correo = readLine() ?: cliente.correo

        println("Cliente actualizado correctamente.\n")
    }

    // DELETE - Eliminar cliente
    fun eliminarCliente() {

        mostrarClientes()

        println("Ingrese el ID del cliente que desea eliminar:")
        val id = readLine()?.toIntOrNull()

        if (id == null  id !in listaClientes.indices) {
            println("Cliente no encontrado.\n")
            return
        }

        listaClientes.removeAt(id)

        println("Cliente eliminado correctamente.\n")
    }
}
