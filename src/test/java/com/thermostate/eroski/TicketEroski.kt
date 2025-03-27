package com.thermostate.eroski

import com.thermostate.eroski.application.HandleNewBill
import com.thermostate.eroski.domain.TicketRepository
import com.thermostate.eroski.infrastructure.Items
import com.thermostate.eroski.infrastructure.ItemsRepo
import com.thermostate.eroski.infrastructure.PdfToTextParse
import com.thermostate.eroski.infrastructure.ticketmodels.EroskiV1Loader
import com.thermostate.eroski.infrastructure.ticketmodels.EroskiV2Loader
import com.thermostate.shared.events.domain.NewBillArrived
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.io.File
import java.util.*

class TicketEroski {
    lateinit var repo: Items
    lateinit var ticketRepository: TicketRepository
    lateinit var handleNewBill: HandleNewBill
    lateinit var loader: EroskiV1Loader
    lateinit var loader2: EroskiV2Loader
    lateinit var pdfParser: PdfToTextParse

    @BeforeEach
    fun setup() {
        loader = EroskiV1Loader()
        loader2 = EroskiV2Loader()
        pdfParser = PdfToTextParse()
        repo = mock(Items::class.java)
        ticketRepository = ItemsRepo(repo)
        handleNewBill = HandleNewBill(listOf(loader, loader2), pdfParser, ticketRepository)
    }

    //@Test
    fun `ticket eroski V1 should be added to database`() {
        println("Current working directory: " + System.getProperty("user.dir"))
        val file = File(Objects.requireNonNull(this.javaClass.getClassLoader().getResource("Compra.pdf")).toURI());

        val event = NewBillArrived(file.absolutePath)

        handleNewBill.handle(event)

        verify(repo, times(44)).save(any())
    }
    //@Test
    fun `ticket eroski V2 should be added to database`() {
        println("Current working directory: " + System.getProperty("user.dir"))
        val file = File(Objects.requireNonNull(this.javaClass.getClassLoader().getResource("Compra2.pdf")).toURI());
        val event = NewBillArrived(file.absolutePath)

        handleNewBill.handle(event)

        verify(repo, times(5)).save(any())
    }
}
