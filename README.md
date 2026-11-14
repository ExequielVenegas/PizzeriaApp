# PizzeriaApp — Semana 4

Proyecto Android Kotlin con Jetpack Compose, creado a partir de Empty Activity.

## Ejecutar
1. Abrir esta carpeta en Android Studio y esperar la sincronización de Gradle.
2. En Device Manager, crear o seleccionar un AVD (por ejemplo Pixel 4) con API 31 o superior.
3. Iniciar el emulador y ejecutar la configuración app.

La pantalla muestra el mensaje solicitado, el catálogo de pizzas, cantidades de 1 a 100,
subtotal, descuento, IVA y total. Confirmar crea un pedido de demostración con cliente de
ejemplo, comprueba existencias, consume inventario y muestra el resumen en preparación.
Los datos de la demostración viven en memoria; se reinician al recrear la actividad.

## Integración
Se reutilizan las clases de semana 3 en cl.duoc.pizzeriaapp.modelo y .servicio:
Producto, Pizza, Combo, Cliente, Usuario, Pedido, Repartidor, Pizzeria y Registro.
Se mantienen validaciones, igualdad, desestructuración, operadores, promociones e inventario.