package com.thermostate.eroski.infrastructure.controllers

import com.thermostate.schedules.domain.events.EventBus
import com.thermostate.shared.events.domain.NewBillArrived
import lombok.AllArgsConstructor
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@RestController
@CrossOrigin
@AllArgsConstructor
class PdfSendingController(val eventBus: EventBus) {

    @PostMapping("/upload")
    fun uploadFile(
        @RequestParam("fileName") fileName: String,
        @RequestPart("file") file: MultipartFile) {
        println("Received file: $fileName")
        println("File size: ${file.size} bytes")
        val filePath: Path = Paths.get("/tmp/$fileName")
        Files.write(filePath, file.bytes)
        eventBus.emit(NewBillArrived("/tmp/$fileName"))
    }
}