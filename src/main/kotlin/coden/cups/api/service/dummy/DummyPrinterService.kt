package coden.cups.api.service.dummy

import coden.cups.api.PrintJob
import coden.cups.api.PrintParams
import coden.cups.api.Printer
import coden.cups.api.PrinterService
import org.slf4j.LoggerFactory
import java.io.FileOutputStream
import java.io.InputStream

object DummyPrinterService : PrinterService {
    private val printer = Printer("DummyDumDum", null, null, null)
    private val log = LoggerFactory.getLogger(DummyPrinterService::class.java)

    override fun getPrinters(): List<Printer> {
        return listOf(printer)
    }

    override fun getPrinter(name: String): Printer {
        return printer
    }

    override fun createJob(printerName: String, data: InputStream, params: PrintParams): PrintJob {
        FileOutputStream("dummy.pdf").use {
            it.write(data.readAllBytes())
        }
        return PrintJob(0)
    }
}