package com.thermostate.eroski.infrastructure

import com.thermostate.eroski.domain.Ticket
import com.thermostate.eroski.domain.TicketRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ItemsRepo(val items: Items) : TicketRepository{

    @Transactional
    override fun save(ticket: Ticket) {
        ticket.items.forEach {
            items.save(ItemJpa.fromDomain(it, ticket.id))
        }
    }
}
