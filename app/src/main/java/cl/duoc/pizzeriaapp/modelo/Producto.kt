package cl.duoc.pizzeriaapp.modelo

open class Producto(
    val nombre: String,
    val precio: Double,
    ingredientes: Map<String, Int>,
    val categoria: String = "pizza"
) {
    val ingredientes = ingredientes.toMap()

    init {
        require(
            categoria in setOf(
                "pizza",
                "bebida",
                "ingrediente",
                "combo"
            )
        ) { "Categoría inválida." }; require(nombre.isNotBlank() && nombre == nombre.trim()) { "Nombre obligatorio." }
        require(precio.isFinite() && precio > 0 && precio <= 1_000_000_000) { "Precio inválido." }
        require(this.ingredientes.isNotEmpty() && this.ingredientes.all { it.key.isNotBlank() && it.value > 0 }) { "Receta inválida." }
    }

    final override fun equals(other: Any?) = other is Producto && nombre == other.nombre && categoria == other.categoria
    final override fun hashCode() = 31 * nombre.hashCode() + categoria.hashCode()
    fun detallarProducto(): String =
        "$nombre | Precio: $precio | Ingredientes: ${ingredientes.entries.joinToString { "${it.key}: ${it.value}" }}"
}


