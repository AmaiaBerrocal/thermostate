package com.thermostate.eroski.domain

import org.apache.pdfbox.Loader
import org.apache.pdfbox.text.PDFTextStripper

class PDFParser {
    fun extractDataFromCompraPdf(filePath: String) {
        val regex = Regex("^\\d+\\.\\d{2}$")
        val lines = obtainStringFromFile(filePath).split("\n")
        var ticket = Ticket()
        lines.map {
            val words = it.split("\\s+".toRegex())
            if (words.size > 3 &&
                regex.matches(words[words.size-1]) &&
                regex.matches(words[words.size-2])) {
                ticket.add(Item(words))
            }
        }
    }
    fun obtainStringFromFile(filePath: String): String {
        val document = Loader.loadPDF(java.io.File(filePath))
        val stripper = PDFTextStripper()
        val text = stripper.getText(document)
        document.close()
        return text
    }
}
