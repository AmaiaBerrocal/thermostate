package com.thermostate.eroski.domain

import java.util.*

class Ticket (val items: List<Item>, val id: String) {

    companion object {
        fun buildTicket(lines: List<String>, ticketLoader: TicketLoader): Ticket {
            val ticket = Ticket(
                id = UUID.randomUUID().toString(),
                items = ticketLoader.loadItems(lines)
            )
            return ticket
        }
    }

    fun persist(repository: TicketRepository) {
        repository.save(this)
    }
}
