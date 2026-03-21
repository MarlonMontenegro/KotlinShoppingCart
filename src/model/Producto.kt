package model

data class Producto(
    var nombre: String,
    var descripcion: String,
    var precio: Double,
    var cantidad: Int
) {

    fun mostrarProducto() {
        println("Nombre: $nombre")
        println("Descripcion: $descripcion")
        println("Precio: $precio")
        println("Cantidad disponible: $cantidad")
        println("-----------------------------")
    }

}
