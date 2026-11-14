package cl.duoc.pizzeriaapp

import cl.duoc.pizzeriaapp.modelo.*
import cl.duoc.pizzeriaapp.servicio.Pizzeria
import org.junit.Assert.*
import org.junit.Test
import java.math.BigDecimal
import java.time.LocalDate

class PizzeriaTest {
    private val cliente = Cliente("Ana", "Local", "569123456789", "ana@example.com")
    private val pizza = Pizza("Margarita", 7000.0, mapOf("Masa" to 1, "Queso" to 2))

    @Test fun promocionYTotalDelPedido() {
        val pedido = Pedido(1, cliente, listOf(pizza), fecha = LocalDate.of(2026, 11, 10))
        assertEquals(BigDecimal("1050"), pedido.descuento)
        assertEquals(BigDecimal("1131"), pedido.impuestos)
        assertEquals(BigDecimal("7081"), pedido.calcularTotal())
        val fuera = Pedido(2, cliente, listOf(pizza), fecha = LocalDate.of(2026, 12, 1))
        assertEquals(BigDecimal.ZERO, fuera.descuento)
    }

    @Test fun confirmarDescuentaInventarioYAvanzaEstado() {
        val sistema = Pizzeria(mapOf("Masa" to 2, "Queso" to 4), emptyList())
        val pedido = Pedido(1, cliente, List(2) { pizza })
        sistema.registrarPedido(pedido)
        sistema.procesarPedido(pedido)
        assertEquals(EstadoPedido.EN_PREPARACION, pedido.estado)
        assertEquals(mapOf("Masa" to 0, "Queso" to 0), sistema.consultarInventario())
    }

    @Test fun stockInsuficienteConservaExistencias() {
        val sistema = Pizzeria(mapOf("Masa" to 1, "Queso" to 1), emptyList())
        val pedido = Pedido(1, cliente, listOf(pizza))
        sistema.registrarPedido(pedido)
        try {
            sistema.procesarPedido(pedido)
            fail("Debe rechazar el pedido")
        } catch (_: IllegalArgumentException) {
            assertEquals(mapOf("Masa" to 1, "Queso" to 1), sistema.consultarInventario())
            assertEquals(EstadoPedido.PENDIENTE, pedido.estado)
        }
    }

    @Test fun combinacionConservaOriginalesYEvitaDuplicados() {
        val fecha = LocalDate.of(2026, 11, 10)
        val primero = Pedido(1, cliente, listOf(pizza), fecha = fecha)
        val segundo = Pedido(2, cliente, listOf(pizza), fecha = fecha)
        assertEquals(2, (primero + segundo).productos.size)
        assertEquals(1, primero.productos.size)
        assertEquals(1, setOf(pizza, Pizza("Margarita", 8000.0, mapOf("Masa" to 1))).size)
    }
}
