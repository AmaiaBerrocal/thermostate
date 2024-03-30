package com.thermostate.eroski.infrastructure

import com.thermostate.eroski.domain.DataLoader
import org.apache.pdfbox.Loader
import org.apache.pdfbox.text.PDFTextStripper

class LoadFromFile : DataLoader {
    override fun load(filePath: String): String {
        val document = Loader.loadPDF(java.io.File(filePath))
        val stripper = PDFTextStripper()
        val text = stripper.getText(document)
        document.close()
        return text
    }
}
