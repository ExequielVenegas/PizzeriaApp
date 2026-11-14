package cl.duoc.pizzeriaapp.modelo

class Cliente(nombre: String, val direccion: String, telefono: String, correo: String) :
    Usuario(nombre.trim(), "${validarTelefono(telefono)} | ${validarCorreo(correo)}") {
    val telefono = validarTelefono(telefono)
    val correo = validarCorreo(correo)

    init {
        require(direccion.isNotBlank() && direccion.length <= 200) { "Indica una dirección de hasta 200 caracteres." }
    }

    override fun descripcion() = "Cliente: ${super.descripcion()} | Dirección: $direccion"
    operator fun component1() = nombre
    operator fun component2() = correo
    operator fun component3() = telefono
    override fun equals(other: Any?) = other is Cliente && nombre == other.nombre && correo == other.correo
    override fun hashCode() = 31 * nombre.hashCode() + correo.hashCode()

    companion object {
        // El formato solicitado tiene dos dígitos de país y diez de número nacional.
        fun validarTelefono(valor: String): String {
            require(
                valor.trim().matches(Regex("\\+?[0-9 ()-]+"))
            ) { "Teléfono inválido: usa dígitos y separadores + ( ) -." }
            val digitos = valor.filter { it in '0'..'9' }
            require(digitos.length == 12) { "Teléfono inválido: ingresa 12 dígitos (2 de país y 10 nacionales), por ejemplo +56 (912) 345-6789." }
            return "+${digitos.take(2)} (${digitos.substring(2, 5)}) ${digitos.substring(5, 8)}-${digitos.substring(8)}"
        }

        fun validarCorreo(valor: String): String {
            val texto = valor.trim()
            require(texto.length <= 254 && texto.matches(Regex("[A-Za-z0-9_%+-]+(?:\\.[A-Za-z0-9_%+-]+)*@[A-Za-z0-9]+(?:[A-Za-z0-9-]*[A-Za-z0-9])?(?:\\.[A-Za-z0-9]+(?:[A-Za-z0-9-]*[A-Za-z0-9])?)+"))) {
                "Correo inválido. Usa el formato nombre@dominio.com."
            }
            return texto
        }
    }
}

