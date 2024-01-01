package coden.brother.facade.service

import coden.brother.facade.model.PrinterPayload
import coden.cups.api.PrinterApi
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class PrinterService(private val api: PrinterApi) {

    fun getAllPrinters(): Flux<PrinterPayload> {
        return Flux
            .fromIterable(api.getPrinters())
            .map { PrinterPayload(
                it.name ?: "N/A",
                it.description ?: "N/A",
                it.url ?: "N/A",
                it.location ?: "N/A",
                it.isDefault
            ) }
    }
}