package cl.duoc.pizzeriaapp.servicio

import cl.duoc.pizzeriaapp.modelo.*

class Registro {
    private val clientes = linkedSetOf<Cliente>()
    private val productos = linkedSetOf<Producto>()
    fun registrar(cliente: Cliente): Boolean = clientes.add(cliente)
    fun registrar(producto: Producto): Boolean = productos.add(producto)
    fun consultarClientes() = clientes.toSet()
    fun consultarProductos() = productos.toSet()
}

fun inspeccionar(tipo: Class<*>): String = buildString {
    appendLine("Reflection: ${tipo.simpleName}")
    val jerarquia = generateSequence(tipo) { it.superclass }.takeWhile { it != Any::class.java }.toList()
    appendLine("Propiedades: " + jerarquia.flatMap { it.declaredFields.toList() }
        .filterNot { it.isSynthetic || java.lang.reflect.Modifier.isStatic(it.modifiers) }
        .map { it.name }.distinct().sorted().joinToString())
    appendLine("Métodos: " + tipo.methods.filterNot { it.isSynthetic }
        .map { it.toGenericString() }.distinct().sorted().joinToString("\n"))
}

