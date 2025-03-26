package com.thermostate.eroski.infrastructure

import com.thermostate.eroski.domain.Parser
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Path

@Component(value = "PdfParser")
class PdfToTextParse : Parser {

    override fun parse(file: String): List<String> {
        val fileTxt = file.removeSuffix(".pdf") + ".txt"
        val commandToParse = "pdftotext -layout $file $fileTxt"

        CommandExecutor.execute(commandToParse)
        return Files.readAllLines(Path.of(fileTxt))
    }
}