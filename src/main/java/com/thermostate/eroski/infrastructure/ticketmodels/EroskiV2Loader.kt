package com.thermostate.eroski.infrastructure.ticketmodels

import com.thermostate.eroski.domain.Item
import com.thermostate.eroski.domain.TicketLoader
import org.springframework.stereotype.Component
import java.util.regex.Pattern

@Component("EroskiV2")
class EroskiV2Loader: TicketLoader {

    override fun hasKey(lines: List<String>) : Boolean =
        lines.any { it.contains("-----------FACTURA SIMPLIFICADA-----------")}

    override fun loadItems(lines: List<String>): List<Item> {
        val items: MutableList<Item> = ArrayList()

        var parsingProducts = false

        run loop@{
            lines.forEach {
                var line = it.trim()
                // Activa el flag cuando empiece la tabla de productos
                if (line.startsWith("KOP.")) {
                    parsingProducts = true
                    return@forEach
                }

                // Sale si detecta una línea de separación después de productos
                if (parsingProducts && line.startsWith("------------------------------------------")) {
                    return@loop
                }
                if (parsingProducts && !line.isBlank()) {
                    val m = Pattern.compile( // OPCIONAL: Número de unidades al principio (con espacio o tabulación)
                        ("^(?:(\\d+)\\s+)?" // Nombre del producto (letras, números, %, puntos, guiones, etc.)
                                + "([A-ZÁÉÍÓÚÑa-z0-9 \\-./%]+?)" // PRECIO UNITARIO opcional
                                + "(?:\\s+(\\d+,\\d{2}))?" // PRECIO TOTAL (obligatorio)
                                + "\\s+(\\d+,\\d{2})" // DESCUENTO ING.CLUB opcional
                                + "(?:\\s+(\\d+,\\d{2}))?")
                    ).matcher(line)

                    if (m.find()) {
                        val unidades = if (m.group(1) != null) Integer.parseInt(m.group(1)) else 1
                        val producto = m.group(2).trim()


                        // Parseo de precios (reemplaza la coma por el punto)
                        val precioPorUnidad = if (m.group(3) != null) (java.lang.Double.parseDouble(
                            m.group(3).replace(",", ".")
                        ) * 100).toInt() else
                            (java.lang.Double.parseDouble(
                                m.group(4).replace(",", ".")
                            ) * 100).toInt() / unidades // si no viene el precio unitario lo calculas

                        val descuento = if (m.group(5) != null) (java.lang.Double.parseDouble(
                            m.group(5).replace(",", ".")
                        ) * 100).toInt() else
                            0

                        items.add(Item(unidades, producto, precioPorUnidad, descuento, producto, "EroskiV2"))
                    }
                }
            }
        }
        return items
    }
}