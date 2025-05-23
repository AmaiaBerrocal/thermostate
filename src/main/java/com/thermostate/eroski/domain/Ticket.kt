package com.thermostate.eroski.domain

import java.util.*

class Ticket (val items: List<Item>, val id: String) {

    companion object {
        fun buildTicket(lines: List<String>, ticketLoaders: List<TicketLoader>): Ticket {
            val ticketLoader = ticketLoaders.filter { it.hasKey(lines) }.firstOrNull() ?: throw IllegalArgumentException("No loader found")
            val ticket = Ticket(
                id = ticketLoader.findId(lines),
                items = ticketLoader.loadItems(lines)
            )
            return ticket
        }
    }

    fun persist(repository: TicketRepository) {
        repository.save(this)
    }
}
