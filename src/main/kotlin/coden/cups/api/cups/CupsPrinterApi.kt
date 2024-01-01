package coden.cups.api.cups

import coden.cups.api.Printer
import coden.cups.api.PrinterApi
import org.cups4j.CupsClient

class CupsPrinterApi(host: String, port: Int): PrinterApi {

    private val client = CupsClient(host, port)

    override fun getPrinters(): List<Printer> {
        return client
            .printers
            .map {
                Printer(it.name, it.printerURL.toExternalForm(), it.description, it.location, it.isDefault)
            }
    }
}