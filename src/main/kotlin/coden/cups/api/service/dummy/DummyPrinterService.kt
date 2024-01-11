package coden.cups.api.service.dummy

import coden.cups.api.PrintJob
import coden.cups.api.PrintParams
import coden.cups.api.Printer
import coden.cups.api.PrinterService
import coden.cups.api.utils.failure
import coden.cups.api.utils.success
import org.slf4j.LoggerFactory
import java.io.FileOutputStream
import java.io.InputStream

object DummyPrinterService : PrinterService {
    private val printer = Printer("DummyDumDum", null, null, null)
    private val jobs = mutableListOf<PrintJob>()
    private val log = LoggerFactory.getLogger(DummyPrinterService::class.java)

    override fun getPrinters(): Result<List<Printer>> {
        return Result.success(listOf(printer))
    }

    override fun getPrinter(name: String): Result<Printer> {
        return Result.success(printer)
    }

    override fun createJob(printerName: String, data: InputStream, params: PrintParams): Result<PrintJob> {
        FileOutputStream("dummy.pdf").use {
            it.write(data.readAllBytes())
        }
        return PrintJob(jobs.size, "dummy-job","successful", true).also {
            jobs.add(it)
        }.success()
    }

    override fun getJobs(printerName: String): Result<List<PrintJob>> {
        return jobs.success()
    }

    override fun getJob(id: Int): Result<PrintJob> {
        return jobs.getOrNull(id)?.success() ?: failure()
    }
}