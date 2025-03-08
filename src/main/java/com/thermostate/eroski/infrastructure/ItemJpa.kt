package com.thermostate.eroski.infrastructure

import com.thermostate.eroski.domain.Item2
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import lombok.Getter
import java.util.*

@Entity
@Table(name = "Items")
@Getter
class ItemJpa(
    @Column(name="ticket_id") val ticket: String,
    @Id val id: UUID,
    @Column(name="units") val units: Int,
    @Column(name="product") val product: String,
    @Column(name="unitPrize") val unitPrize : Int,
    @Column(name="discount") val discount: Int) {

    companion object {
        fun fromDomain(item2: Item2, ticketId: String): ItemJpa {
            return ItemJpa(
                ticketId,
                UUID.randomUUID(),
                item2.units,
                item2.product,
                item2.precioUnit,
                item2.discounts
            )
        }
    }
}
