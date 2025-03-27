package com.thermostate.shared.events.domain

import java.io.Serializable
import java.util.*

class NewBillArrived(val fileName: String) : DomainEvent(fileName) {
    override fun eventName(): String {
        return "NEW_BILL_ARRIVED"
    }

    override fun toPrimitives(): Map<String, Serializable> {
        return mapOf()
    }

    override fun fromPrimitives(
        aggregateId: String?,
        body: HashMap<String, Serializable>?,
        eventId: String?,
        occurredOn: String?
    ): DomainEvent {
        TODO("Not yet implemented")
    }

}
