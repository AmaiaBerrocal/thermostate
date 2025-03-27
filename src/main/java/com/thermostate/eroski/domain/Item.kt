package com.thermostate.eroski.domain

data class Item(val numberOfItems: Int,
                val description: String,
                val amountPerUnit: Int,
                var discount: Int,
                val alias: String,
                val type: String,
                val ticketId: String)