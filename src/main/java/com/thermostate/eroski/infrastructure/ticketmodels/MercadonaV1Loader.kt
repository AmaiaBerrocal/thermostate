package com.thermostate.eroski.infrastructure.ticketmodels

import com.thermostate.eroski.domain.Item
import com.thermostate.eroski.domain.TicketLoader
import org.springframework.stereotype.Component
import java.util.regex.Pattern

@Component("MercadonaV1Loader")
class MercadonaV1Loader: TicketLoader {
    lateinit var id: String

    override fun findId(lines: List<String>): String {
        id = ""
        val idRegex = Pattern.compile("\\b\\d{4}-\\d{3}-\\d{6}\\b")
        for (line in lines) {
            val matcher = idRegex.matcher(line)
            if (matcher.find()) {
                id = matcher.group()
            }
        }
        return id
    }

    override fun hasKey(lines: List<String>) = lines.any { it.contains("MERCADONA")}

    override fun loadItems(lines: List<String>): List<Item> {

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

        for (line in lines) {
            var line = line
            line = line.trim { it <= ' ' }

            val productMatcher = productPattern.matcher(line)
            val weightMatcher = weightPattern.matcher(line)

            if (productMatcher.find()) {
                val unidades = productMatcher.group(1).toInt()
                val producto = productMatcher.group(2).trim { it <= ' ' }
                val precioPorUnidad = if (productMatcher.group(3) != null) (productMatcher.group(3).replace(",", ".")
                    .toDouble() * 100).toInt() else 0

                val precioTotal = (productMatcher.group(4).replace(",", ".").toDouble() * 100).toInt()
                val precio = if (unidades > 1) precioPorUnidad else precioTotal
                items.add(Item(unidades, producto, precio, 0, producto, "MERCADONAV1", id))
                previousLine = producto
            } else if (weightMatcher.find() && previousLine != null) {
                val pesoKg = weightMatcher.group(1).replace(",", ".").toDouble()
                val precioPorKg = (weightMatcher.group(2).replace(",", ".").toDouble() * 100).toInt()

                items.add(Item((pesoKg * 1000).toInt(), previousLine, precioPorKg, 0, previousLine, "MERCADONAV1", id))
                previousLine = null // Se resetea después de capturar el peso
            } else {
                previousLine = line
            }
        }
        return items
    }

}
