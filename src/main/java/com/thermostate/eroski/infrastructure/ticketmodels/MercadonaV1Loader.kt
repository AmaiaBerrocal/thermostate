package com.thermostate.eroski.infrastructure.ticketmodels

import java.util.regex.Pattern

object MercadonaV1Loader {
    @JvmStatic
    fun main(args: Array<String>) {
        val ticket = """
MERCADONA, S.A. A-46103834
              C/ SIERRA DE ANDIA 10
             01010 VITORIA-GASTEIZ
              TELÉFONO: 945318995
          01/04/2025 19:01  OP: 3472084
      FACTURA SIMPLIFICADA: 4334-021-423648

    Descripción                    P. Unit    Importe
  1 FILETE MERLUZA CABO                          4,95
  1 PATATAS CORTE GRUESO                         3,10
  4 PROTEINA 0% NATURAL               1,41       5,64
  1 T.CHERRY 500 GR                              1,98
  1 SANDÍA PARTIDA B/S                           4,17
  2 JAMON C.CALIDAD EXTR              2,04       4,08
  1 INF TE VERDE                                 0,75
  1 PENNE RIGATE                                 0,80
  1 COCA COLA LIGHT P -9                         7,47
  1 MACARRON FINO                                0,80
  1 MINI RELLENO LECHE                           1,95
  2 CEBOLLA TIERNA                    1,35       2,70
  1 MOUSSE PROTEIN CHOCO                         1,30
  1 PECHUGA FAMILIAR                             7,85
  1 ZANAHORIA RALLADA                            0,94
  1 GOURMET MAXI                                 1,76
  2 PANECILLO 11 UDS                  1,14       2,28
  1 PAN ACEITE DE OLIVA                          0,57
  1 GEL-CHAMPÚ DUCHA                             1,00
  1 + PROTEÍNAS FRESA                            1,47
  1 PLATANO
        1,322 kg                 3,20 €/kg       4,23
  1 MANZANA GOLDEN
        1,030 kg                 2,10 €/kg       2,16
                                TOTAL (€)       61,95
                       TARJETA BANCARIA         61,95
     IVA        BASE IMPONIBLE (€)      CUOTA (€)

""".trimIndent()

        val items: MutableList<Item> = ArrayList()
        var previousLine: String? = null

        // Regex para capturar productos normales
        val productPattern = Pattern.compile(
            "^(\\d+)\\s+([A-ZÁÉÍÓÚÑa-z0-9 %+.,-]+?)" +  // Cantidad + Nombre del producto
                    "(?:\\s+(\\d+,\\d{2}))?" +  // Precio unitario (opcional)
                    "\\s+(\\d+,\\d{2})$" // Precio total (obligatorio)
        )

        // Regex para productos por peso
        val weightPattern = Pattern.compile(
            "^\\s*(\\d+,\\d{3}) kg\\s+(\\d+,\\d{2}) €/kg\\s+(\\d+,\\d{2})$"
        )

        for (line in ticket.split("\\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
            var line = line
            line = line.trim { it <= ' ' }

            val productMatcher = productPattern.matcher(line)
            val weightMatcher = weightPattern.matcher(line)

            if (productMatcher.find()) {
                val unidades = productMatcher.group(1).toInt()
                val producto = productMatcher.group(2).trim { it <= ' ' }
                val precioPorUnidad = if (productMatcher.group(3) != null) (productMatcher.group(3).replace(",", ".")
                    .toDouble() * 100).toInt() else
                    0 // Se calculará después en caso de productos por peso

                val precioTotal = (productMatcher.group(4).replace(",", ".").toDouble() * 100).toInt()

                items.add(Item(unidades, producto, precioPorUnidad, precioTotal))
                previousLine = producto
            } else if (weightMatcher.find() && previousLine != null) {
                val pesoKg = weightMatcher.group(1).replace(",", ".").toDouble()
                val precioPorKg = (weightMatcher.group(2).replace(",", ".").toDouble() * 100).toInt()
                val precioTotal = (weightMatcher.group(3).replace(",", ".").toDouble() * 100).toInt()

                items.add(Item(1, "$previousLine ($pesoKg kg)", precioPorKg, precioTotal))
                previousLine = null // Se resetea después de capturar el peso
            }
        }

        // Mostrar resultados
        for (item in items) {
            println(item)
        }
    }

    internal class Item(
        var unidades: Int, var producto: String, // en céntimos
        var precioPorUnidad: Int, // en céntimos
        var precioTotal: Int
    ) {
        override fun toString(): String {
            return unidades.toString() + " x " + producto + " @ " + (precioPorUnidad / 100.0) + "€ (Total: " + (precioTotal / 100.0) + "€)"
        }
    }
}
