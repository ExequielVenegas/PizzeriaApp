package cl.duoc.pizzeriaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import cl.duoc.pizzeriaapp.modelo.*
import cl.duoc.pizzeriaapp.servicio.Pizzeria
import cl.duoc.pizzeriaapp.ui.theme.PizzeriaAppTheme
import java.text.NumberFormat
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { PizzeriaAppTheme { PantallaPizzeria() } }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    Text(
        text = "Bienvenidos a la mejor pizzería de la ciudad",
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}

@Composable
fun PantallaPizzeria() {
    val catalogo = remember {
        listOf(
            Pizza("Margarita", 7000.0, mapOf("Masa" to 1, "Queso" to 2, "Tomate" to 1)),
            Pizza("Pepperoni", 8500.0, mapOf("Masa" to 1, "Queso" to 2, "Pepperoni" to 1)),
            Pizza("Vegetariana", 8000.0, mapOf("Masa" to 1, "Queso" to 1, "Tomate" to 2))
        )
    }
    val pizzeria = remember {
        Pizzeria(mapOf("Masa" to 100, "Queso" to 200, "Tomate" to 200, "Pepperoni" to 100), emptyList())
    }
    var seleccion by rememberSaveable { mutableStateOf(0) }
    var cantidad by rememberSaveable { mutableStateOf("1") }
    var resultado by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var siguienteId by remember { mutableStateOf(1) }
    val unidades = cantidad.toIntOrNull()
    val valida = unidades != null && unidades in 1..Pedido.MAXIMO_PIZZAS
    val cliente = remember { Cliente("Cliente de demostración", "Retiro en local", "569123456789", "cliente@example.com") }
    val propuesta = if (valida) Pedido(siguienteId, cliente, List(unidades!!) { catalogo[seleccion] }) else null
    val moneda = remember { NumberFormat.getCurrencyInstance(Locale.

    forLanguageTag("es-CL")) }

    Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Column(
            Modifier.padding(padding).fillMaxSize().verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text("PIZZERÍA DUOC", style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                Surface(color = MaterialTheme.colorScheme.secondaryContainer, shape = RoundedCornerShape(50)) {
                    Text("Retiro en local", Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.labelMedium)
                }
            }
            Greeting()
            Text("Tu favorita, recién preparada.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(4.dp))
            Text("Elige tu pizza", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            val descripciones = listOf("Queso y tomate · un clásico", "Queso y pepperoni · llena de sabor", "Queso y tomate · una opción ligera")
            catalogo.forEachIndexed { indice, pizza ->
                val elegida = seleccion == indice
                Card(
                    onClick = { seleccion = indice },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (elegida) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                    ),
                    border = BorderStroke(if (elegida) 2.dp else 1.dp,
                        if (elegida) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant)
                ) {
                    Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = elegida, onClick = null)
                        Spacer(Modifier.width(14.dp))
                        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(pizza.nombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text(descripciones[indice], style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Spacer(Modifier.width(8.dp))
                        Text(moneda.format(pizza.precio), style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold)
                    }
                }
            }
            OutlinedTextField(
                value = cantidad,
                onValueChange = { cantidad = it.take(10) },
                label = { Text("¿Cuántas pizzas?") },
                supportingText = { Text(if (valida) "Puedes pedir de 1 a 100 pizzas." else "Ingresa un número entero entre 1 y 100.") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(16.dp),
                isError = !valida,
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            if (propuesta != null) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Tu pedido", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        FilaResumen("$unidades × ${catalogo[seleccion].nombre}", moneda.format(propuesta.subtotal))
                        FilaResumen("Descuento", "− ${moneda.format(propuesta.descuento)}")
                        FilaResumen("IVA · 19%", moneda.format(propuesta.impuestos))
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Total", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                            Text(moneda.format(propuesta.calcularTotal()), style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
            Text("15% de descuento en pizzas · 1–30 nov. 2026",
                style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
            Text("Aplicamos el mayor beneficio entre promoción y descuento por cantidad.",
                style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Button(
                enabled = valida,
                modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp),
                shape = RoundedCornerShape(16.dp),
                onClick = {
                    error = null
                    try {
                        val pedido = requireNotNull(propuesta)
                        // Comprueba existencias antes de registrar un pedido que no pueda prepararse.
                        val faltantes = pizzeria.calcularIngredientes(pedido.productos)
                            .filter { (nombre, necesario) -> (pizzeria.consultarInventario()[nombre] ?: 0) < necesario }
                        require(faltantes.isEmpty()) { "Stock insuficiente. Prueba con una cantidad menor." }
                        pizzeria.registrarPedido(pedido)
                        pizzeria.procesarPedido(pedido)
                        resultado = pedido.resumen()
                        siguienteId++
                    } catch (e: IllegalArgumentException) {
                        error = e.message ?: "Revisa los datos del pedido."
                    } catch (e: IllegalStateException) {
                        error = e.message ?: "No se pudo confirmar el pedido."
                    }
                }
            ) { Text("Confirmar pedido", fontWeight = FontWeight.Bold) }
            error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            if (resultado.isNotEmpty()) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                    shape = RoundedCornerShape(20.dp), modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("¡Pedido confirmado!", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Text(resultado, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            Text("Pedido de demostración · Los datos se reinician al cerrar la pantalla.",
                style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun FilaResumen(etiqueta: String, valor: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(etiqueta, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.width(12.dp))
        Text(valor, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PizzeriaAppTheme { PantallaPizzeria() }
}

