package com.thermostate.eroski.domain

interface DataLoader {
    fun load(filePath: String): String
}
