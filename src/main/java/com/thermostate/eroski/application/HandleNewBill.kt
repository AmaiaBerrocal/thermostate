package com.thermostate.eroski.application

import com.thermostate.eroski.domain.*
import com.thermostate.shared.events.domain.NewBillArrived
import com.thermostate.shared.events.infrastructure.EventHandler
import org.springframework.stereotype.Component

@Component
class HandleNewBill(val loader: TicketLoader, val pdfParser: PdfParser, val repository : TicketRepository) : EventHandler<NewBillArrived>() {

    override fun handle(newBillArrived: NewBillArrived) {
        val fileLines = pdfParser.parse(newBillArrived.fileName)
        val ticket = Ticket.buildTicket(fileLines, loader)
        ticket.persist(repository)
    }
}
