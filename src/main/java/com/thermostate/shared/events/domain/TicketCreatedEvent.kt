package com.thermostate.shared.events.domain

import java.io.Serializable
import java.util.HashMap
import java.util.UUID

class TicketCreatedEvent(aggregateId: String, val ticketId: String): TicketEvent(aggregateId) {

    override fun eventName(): String {
        return "TICKET_CREATED_EVENT"
    }

    override fun toPrimitives(): Map<String?, Serializable?>? {
        TODO("Not yet implemented")
    }

    override fun fromPrimitives(
        aggregateId: String?,
        body: HashMap<String?, Serializable?>?,
        eventId: String?,
        occurredOn: String?
    ): DomainEvent? {
        TODO("Not yet implemented")
    }

    override fun toString(): String {
        return "TicketCreatedEvent(ticketId='$ticketId', aggregateId='${super.aggregateId()}')"
    }
}