package com.thermostate.eroski.infrastructure

import com.thermostate.eroski.domain.PdfParser
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Path

@Component
class PdfToTextParse : PdfParser {

    override fun parse(filePdf: String): List<String> {
        val fileTxt = filePdf.removeSuffix(".pdf") + ".txt"
        val commandToParse = "pdftotext -layout $filePdf $fileTxt"

        CommandExecutor.execute(commandToParse)
        return Files.readAllLines(Path.of(fileTxt))
    }
}