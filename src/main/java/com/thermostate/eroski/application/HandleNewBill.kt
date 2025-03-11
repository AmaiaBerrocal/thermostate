package com.thermostate.eroski.application

import com.thermostate.eroski.domain.*
import com.thermostate.shared.events.domain.NewBillArrived
import com.thermostate.shared.events.infrastructure.EventHandler
import org.springframework.stereotype.Component

@Component
class HandleNewBill(val loaders: List<TicketLoader>, val pdfParser: PdfParser, val repository : TicketRepository) : EventHandler<NewBillArrived>() {

    override fun handle(newBillArrived: NewBillArrived) {
        val fileLines = pdfParser.parse(newBillArrived.fileName)
        val ticket = Ticket.buildTicket(fileLines, loaders)
        ticket.persist(repository)
    }
}
