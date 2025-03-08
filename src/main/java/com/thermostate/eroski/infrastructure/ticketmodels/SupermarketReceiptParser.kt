package com.thermostate.eroski.infrastructure.ticketmodels

import com.thermostate.eroski.domain.Item
import com.thermostate.eroski.infrastructure.CommandExecutor
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.util.regex.Pattern

object SupermarketReceiptParser {
    fun parseReceipt(fileLines: List<String>): List<Item> {
        val items: MutableList<Item> = ArrayList()
        val descuentosGenerales: MutableMap<String, Int> = HashMap()
        var parsingProducts = false
        var parsingDiscounts = false

        for (line in fileLines) {
            var line = line
            println(line)
            line = line.trim { it <= ' ' }

            if (line.startsWith("DESCUENTOS POR OFERTA")) {
                parsingProducts = false
                parsingDiscounts = true
                continue
            }

            if (line.contains("€/TOT")) { // Detectamos el encabezado de productos
                parsingProducts = true
                continue
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

                    items.add(Item(unidades, producto, precioPorUnidad, descuento))
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

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        println("Current working directory: " + System.getProperty("user.dir"))
        val base = System.getProperty("user.dir")
        val filePdf = "$base/src/test/resources/Compra.pdf"
        val fileTxt = "$base/src/test/resources/Compra.txt"
        val commandToParse = "pdftotext -layout $filePdf $fileTxt"
        val commandToDelete = "rm $fileTxt"

        if (!Files.exists(Path.of(filePdf))) {
            System.err.println("File not created: $filePdf")
            return
        }

        CommandExecutor.execute(commandToParse)
        val fileLines = Files.readAllLines(Path.of(fileTxt))

        val items = parseReceipt(fileLines)
        for (item in items) {
            println(item)
        }
        println("Total: " + items.stream().mapToInt { i: Item -> i.numberOfItems * i.amountPerUnit - i.discount }
            .sum() / 100.0)
        println(items.size.toString() + " items")
        CommandExecutor.execute(commandToDelete)
    }
}


