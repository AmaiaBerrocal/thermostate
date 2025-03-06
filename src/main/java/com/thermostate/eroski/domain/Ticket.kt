package com.thermostate.eroski.domain

import java.util.*

class Ticket (val items: MutableList<Item> = mutableListOf(), val id: String) {

    companion object {
        val regex = Regex("^\\d+\\.\\d{2}$")

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
                    ticket.add(Item(words))
                }
            }
            return ticket
        }
    }
    fun add(item: Item) {
        items.add(item)
    }

    fun persist(repository: TicketRepository) {
        repository.save(this)
    }
}
