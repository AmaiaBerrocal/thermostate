package com.thermostate.eroski.application

import com.thermostate.eroski.domain.*
import com.thermostate.shared.events.domain.NewBillArrived
import com.thermostate.shared.events.infrastructure.EventHandler
import org.springframework.stereotype.Component

@Component
class HandleNewBill(val dataLoader: DataLoader, val repository : TicketRepository) : EventHandler<NewBillArrived> {

    override fun handle(newBillArrived: NewBillArrived) {
        val ticket = Ticket.buildTicket(newBillArrived.fileName, dataLoader)
        ticket.persist(repository)
    }
}
