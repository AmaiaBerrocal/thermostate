package com.thermostate.eroski.domain

import com.thermostate.shared.events.domain.AggregateRoot
import com.thermostate.shared.events.domain.TicketCreatedEvent

class Ticket (val items: List<Item>, val id: String): AggregateRoot() {

    companion object {
        fun buildTicket(lines: List<String>, ticketLoaders: List<TicketLoader>): Ticket {
            val ticketLoader = ticketLoaders.filter { it.hasKey(lines) }.firstOrNull() ?: throw IllegalArgumentException("No loader found")
            val ticket = Ticket(
                id = ticketLoader.findId(lines),
                items = ticketLoader.loadItems(lines)
            )
            ticket.record(TicketCreatedEvent(ticket.id, ticket.id))
            return ticket
        }
    }

    fun persist(repository: TicketRepository) {
        repository.save(this)
    }

    fun doesNotExist(repository: TicketRepository): Boolean {
        return repository.findByTicketId(id)
    }
}
