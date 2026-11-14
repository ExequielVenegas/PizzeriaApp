package cl.duoc.pizzeriaapp.modelo

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Promocionable(val porcentaje: Int = 15)

fun Producto.porcentajePromocion(): Int =
    javaClass.getAnnotation(Promocionable::class.java)?.porcentaje ?: 0

@Promocionable
class Combo(nombre: String, precio: Double, ingredientes: Map<String, Int>) :
    Producto(nombre, precio, ingredientes, "combo")

