package coden.brother.facade.controller

import coden.brother.facade.model.PrinterPayload
import coden.brother.facade.service.PrinterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/printers")
class PrinterController {

    @Autowired
    lateinit var printerService: PrinterService

    @GetMapping
    fun getAllPrinters(): Flux<PrinterPayload>{
        return printerService
            .getAllPrinters()
    }

}