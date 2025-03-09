package eroski

import com.thermostate.eroski.application.HandleNewBill
import com.thermostate.eroski.domain.TicketRepository
import com.thermostate.eroski.infrastructure.Items
import com.thermostate.eroski.infrastructure.ItemsRepo
import com.thermostate.eroski.infrastructure.PdfToTextParse
import com.thermostate.eroski.infrastructure.ticketmodels.EroskiV1Loader
import com.thermostate.shared.events.domain.NewBillArrived
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class Ticket {
    lateinit var repo: Items
    lateinit var ticketRepository: TicketRepository
    lateinit var handleNewBill: HandleNewBill
    lateinit var loader: EroskiV1Loader
    lateinit var pdfParser: PdfToTextParse

    @BeforeEach
    fun setup() {
        repo = mock(Items::class.java)
        ticketRepository = ItemsRepo(repo)
        handleNewBill = HandleNewBill(loader, pdfParser, ticketRepository)
    }

    @Test
    fun `ticket should be added to database`() {
        println("Current working directory: " + System.getProperty("user.dir"))
        val event = NewBillArrived("./src/test/resources/Compra.pdf")

        handleNewBill.handle(event)

        verify(repo, times(45)).save(any())
    }
}
