package coden.cups.api.service.cups

import coden.cups.api.PrintJob
import coden.cups.api.PrintParams
import coden.cups.api.Printer
import coden.cups.api.PrinterService
import coden.cups.api.utils.failure
import coden.cups.api.utils.success
import org.cups4j.CupsClient
import org.cups4j.CupsPrinter
import org.cups4j.PrintJobAttributes
import org.cups4j.WhichJobsEnum
import java.io.InputStream

private const val USERNAME = "coden"

class CupsPrinterService(properties: CupsPrinterProperties): PrinterService {

    private val client = CupsClient(properties.host, properties.port)
    private val attributes = hashMapOf("job-attributes" to "sides:keyword:one-sided" )

    override fun getPrinters(): Result<List<Printer>> {
        return client
            .printers
            .map { mapPrinter(it) }
            .success()

    }

    override fun getPrinter(name: String): Result<Printer> {
        val printer: CupsPrinter? = client.getPrinter(name)
        return printer?.let { mapPrinter(it).success() } ?: failure()
    }

    private fun mapPrinter(it: CupsPrinter) =
        Printer(it.name, it.printerURL.toExternalForm(), it.description, it.location, it.isDefault)

    override fun createJob(printerName: String, data: InputStream, params: PrintParams): Result<PrintJob> {
        val printer: CupsPrinter = client.getPrinter(printerName) ?: return failure()
        val job = org.cups4j.PrintJob.Builder(data)
            .jobName(params.name)
            .userName(USERNAME)
            .copies(params.copies)
            .pageFormat("iso-a4")
            .attributes(attributes)
            .build();
        val result = printer.print(job)
        return PrintJob(result.jobId,
            params.name,
            result.resultDescription,
            result.isSuccessfulResult
        ).success()
    }

    override fun getJobs(printerName: String): Result<List<PrintJob>> {
        val printer: CupsPrinter = client.getPrinter(printerName) ?: return failure()
        return client.getJobs(printer, WhichJobsEnum.ALL, USERNAME, false)
            .map { mapPrintJob(it) }
            .success()
    }

    override fun getJob(id: Int): Result<PrintJob> {
         return runCatching { client.getJobAttributes(id)}
             .map { mapPrintJob(it) }
    }

    private fun mapPrintJob(attributes: PrintJobAttributes): PrintJob{
        return PrintJob(attributes.jobID,
            attributes.jobName,
            "Submitted successfully", true,
            attributes.jobState.toString())
    }
}