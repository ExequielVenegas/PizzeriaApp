package cl.duoc.pizzeriaapp.modelo

@Promocionable
class Pizza(nombre: String, precio: Double, ingredientes: Map<String, Int>) : Producto(nombre, precio, ingredientes)


