package com.thermostate.eroski.infrastructure.ticketmodels

import com.thermostate.eroski.domain.Item
import com.thermostate.eroski.domain.TicketLoader
import org.springframework.stereotype.Component
import java.util.regex.Pattern

@Component("EroskiV1")
class EroskiV1Loader: TicketLoader {
    lateinit var id: String
    override fun hasKey(lines: List<String>): Boolean = lines.any { it.contains("****EROSKI BOULEVARD****") }

    override fun findId(lines: List<String>): String {
        id = ""
        val idRegex = Pattern.compile("\\b\\d{2} \\d{2} \\d{1} \\d{4}\\b")
        for (line in lines) {
            val matcher = idRegex.matcher(line)
            if (matcher.find()) {
                id = matcher.group()
            }
        }
        return id
    }

    override fun loadItems(lines: List<String>): List<Item> {
        val items: MutableList<Item> = ArrayList()
        val descuentosGenerales: MutableMap<String, Int> = HashMap()
        var parsingProducts = false
        var parsingDiscounts = false
        lines.forEach { fileLine ->
            val line = fileLine.trim { it <= ' ' }

            if (line.startsWith("DESCUENTOS POR OFERTA")) {
                parsingProducts = false
                parsingDiscounts = true
                return@forEach
            }

            if (line.contains("€/TOT")) { // Detectamos el encabezado de productos
                parsingProducts = true
                return@forEach
            }

            if (parsingProducts) {
                val m =
                    Pattern.compile("(\\d+\\t)?\\s*([A-ZÁÉÍÓÚÑ./\\s]+)\\s*(\\d+\\.\\d+)?\\s*(\\d+\\.\\d+)\\s*(-?\\d+\\.\\d+)?")
                        .matcher(line)
                if (m.find()) {
                    val producto = m.group(2).trim { it <= ' ' }
                    var unidades = 1
                    if (line.matches("^\\d+\\s{2,}.*".toRegex())) {
                        unidades = line.substring(0, line.indexOf(" ")).toInt()
                    }
                    val precioTotal = if (unidades > 1) (m.group(4).toDouble() * 100).toInt() else (m.group(3)
                        .toDouble() * 100).toInt()
                    val descuento = if (m.group(5) != null) (m.group(5).toDouble() * 100).toInt() else 0
                    val precioPorUnidad =
                        if (unidades > 1 && m.group(3) != null) (m.group(3).toDouble() * 100).toInt() else precioTotal

                    items.add(
                        Item(unidades,
                            producto,
                            precioPorUnidad,
                            descuento,
                            producto,
                            "EroskiV1",
                            id))
                }
            }

            if (parsingDiscounts) {
                val d = Pattern.compile("([A-ZÁÉÍÓÚÑ./\\s]+)\\s*(-?\\d+\\.\\d+)").matcher(line)
                if (d.find()) {
                    val producto = d.group(1).trim { it <= ' ' }
                    val descuento = (d.group(2).toDouble() * 100).toInt()
                    descuentosGenerales[producto] = descuentosGenerales.getOrDefault(producto, 0) - descuento
                }
            }
        }

        for (item in items) {
            if (descuentosGenerales.containsKey(item.description)) {
                item.discount += descuentosGenerales[item.description]!!
            }
        }

        return items
    }


}