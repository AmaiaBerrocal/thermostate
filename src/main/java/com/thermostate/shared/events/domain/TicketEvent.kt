package com.thermostate.shared.events.domain

import java.io.Serializable

abstract class TicketEvent(aggregateId: String): DomainEvent(aggregateId) {
    override fun eventName(): String {
        return "TICKET_EVENT"
    }
}