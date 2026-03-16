data class Cliente(
    var nombre: String,
    var correo: String
) {

    fun mostrarCliente() {
        println("Nombre: $nombre")
        println("Correo: $correo")
        println("-----------------------------")
    }

}
