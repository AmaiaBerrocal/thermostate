package com.thermostate.eroski.domain

interface TicketLoader {
    fun loadItems(lines: List<String>): List<Item>
}
