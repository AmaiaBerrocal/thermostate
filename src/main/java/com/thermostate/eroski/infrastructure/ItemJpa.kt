package com.thermostate.eroski.infrastructure

import com.thermostate.eroski.domain.Item
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
    @Column(name="type") val type: String,
    @Column(name="alias") val alias: String,
    @Column(name="discount") val discount: Int) {

    companion object {
        fun fromDomain(item: Item, ticketId: String): ItemJpa {
            return ItemJpa(
                ticketId,
                UUID.randomUUID(),
                item.numberOfItems,
                item.description,
                item.amountPerUnit,
                item.type,
                item.alias,
                item.discount
            )
        }
    }
}
