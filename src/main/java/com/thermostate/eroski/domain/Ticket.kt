package com.thermostate.eroski.domain

import java.util.*

class Ticket (val item2s: MutableList<Item2> = mutableListOf(), val id: String) {

    companion object {
        val numberRegex = Regex("^\\d+$")
        val regex = Regex("^\\d+\\.\\d{2}$")

        fun findProduct(words: List<String>): String? {
            if (!numberRegex.matches(words[0])) {
                var product = ""
                var  i = 0
                while (words.size > i && !regex.matches(words[i])) {
                    product += " ${words[i]}"
                    i++
                }
                return product
            }
            return null
        }
        fun buildTicket(path: String, loader: DataLoader) : Ticket {
            return buildTicket(loader.load(path).split("\n"))
        }
        fun buildTicket(lines: List<String>) : Ticket {
            val ticket = Ticket(id = UUID.randomUUID().toString())
            lines.map {
                val words = it.split("\\s+".toRegex())
                if (words.size > 3 &&
                    regex.matches(words[words.size - 1]) &&
                    regex.matches(words[words.size - 2])) {
                    ticket.add(Item2(words))
                } else {
                    findProduct(words)?.let { product ->
                        ticket.item2s.forEach {
                            //println("${it.product} == $product")
                            if (it.product == product) {
                                println(words.last().replace(".", ""))
                                return@map
                            }
                        }
                    }
                }
            }
            lines.forEach(::println)
            return ticket
        }
    }

    fun add(item2: Item2) {
        item2s.add(item2)
    }

    fun persist(repository: TicketRepository) {
        repository.save(this)
    }
}
