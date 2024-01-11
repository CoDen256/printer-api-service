package coden.cups.api.service.cups

import coden.cups.api.PrintParams
import coden.cups.api.Printer
import coden.cups.api.PrinterService
import org.cups4j.CupsClient
import org.cups4j.CupsPrinter
import java.io.InputStream

private const val USERNAME = "coden"

class CupsPrinterService(properties: CupsPrinterProperties): PrinterService {

    private val client = CupsClient(properties.host, properties.port)
    private val attributes = hashMapOf("job-attributes" to "sides:keyword:one-sided" )

    override fun getPrinters(): List<Printer> {
        return client
            .printers
            .map { mapPrinter(it) }
    }

    override fun getPrinter(name: String): Printer {
        return mapPrinter(client.getPrinter(name))
    }

    private fun mapPrinter(it: CupsPrinter) =
        Printer(it.name, it.printerURL.toExternalForm(), it.description, it.location, it.isDefault)

    override fun createJob(printerName: String, data: InputStream, params: PrintParams): coden.cups.api.PrintJob {
        val printer = client.getPrinter(printerName)
        val job = org.cups4j.PrintJob.Builder(data)
            .jobName(params.name)
            .userName(USERNAME)
            .copies(params.copies)
            .pageFormat("iso-a4")
            .attributes(attributes)
            .build();
        val result = printer.print(job)
        return coden.cups.api.PrintJob(result.jobId, result.resultDescription, result.isSuccessfulResult)
    }
}