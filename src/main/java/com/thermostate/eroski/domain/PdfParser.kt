package com.thermostate.eroski.domain

interface PdfParser {
    fun parse(filePdf: String): List<String>
}