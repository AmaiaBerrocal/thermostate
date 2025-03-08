package com.thermostate.eroski.infrastructure

import com.thermostate.eroski.domain.PdfParser

class PdfToTextParse : PdfParser {
    fun parse(filePdf: String): String {
        val fileTxt = filePdf.removeSuffix(".pdf") + ".txt"
        val commandToParse = "pdftotext -layout $filePdf $fileTxt"

        CommandExecutor.execute(commandToParse)
        return fileTxt
    }
}