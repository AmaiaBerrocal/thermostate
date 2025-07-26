package com.thermostate.eroski.domain

interface TicketRepository {
    fun save(ticket: Ticket)
    fun findByTicketId(id: String): Boolean
}
