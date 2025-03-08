package com.thermostate.eroski.domain

import kotlin.properties.Delegates

class Item2 {
    var units by Delegates.notNull<Int>()
    var product: String = ""
    var precioUnit by Delegates.notNull<Int>()
    var discounts : Int = 0

    constructor(words: List<String>) {
        val numberRegex = Regex("^\\d+$")
        val decimalRegex = Regex("^\\d+\\.\\d{2}$")
        if (numberRegex.matches(words[0])) {
            units = words[0].toInt()
            var i = 1
            while(!decimalRegex.matches(words[i])) {
                product += " ${words[i]}"
                i++
            }
            precioUnit = words[i].replace(".", "").toInt()
            i++
            // precio total
            i++
            while(i < words.size && decimalRegex.matches(words[i])) {
                discounts += words[i].replace(".", "").toInt()
                i++
            }
        } else {
            units = 1
            var i = 1
            while(!decimalRegex.matches(words[i])) {
                product += " ${words[i]}"
                i++
            }
            precioUnit = words[i].replace(".", "").toInt()
            i++
            while(i < words.size && decimalRegex.matches(words[i])) {
                discounts += words[i].replace(".", "").toInt()
                i++
            }
        }
        product = product.trim()
    }

    override fun toString(): String {
        return "Item(units=$units, product='$product', precioUnit=$precioUnit, discounts=$discounts)"
    }
}


