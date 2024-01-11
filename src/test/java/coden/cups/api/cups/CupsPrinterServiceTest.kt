package coden.cups.api.cups

import coden.cups.api.PrintParams
import coden.cups.api.service.cups.CupsPrinterProperties
import coden.cups.api.service.cups.CupsPrinterService
import org.cups4j.CupsClient
import org.cups4j.PrintJob
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileInputStream

@Disabled
class CupsPrinterServiceTest {

    val properties = CupsPrinterProperties("rpi-beta.local", 631)
    val service = CupsPrinterService(properties)
    val client = CupsClient(properties.host, properties.port)

    @Test
    fun getPrinters() {
    }

    @Test
    fun getPrinter() {
    }

    @Test
    fun createJob() {
        val file = File("test.pdf")
        service.createJob("MFCL2710DW", FileInputStream(file))
    }

    @Test
    fun createJobPDF() {
        val file = File("test.pdf")
        val r = service.createJob("PDF", FileInputStream(file), PrintParams.Builder().name("test").build())
        println(r.message)
        println(r.success)
        println(r.id)
    }

    @Test
    fun cupsCreateJob() {
        val attributes = hashMapOf("job-attributes" to "sides:keyword:one-sided" )
        val file = File("test.pdf")

        val job = PrintJob.Builder(file.readBytes())
            .jobName("job-name")
            .userName("coden")
            .copies(1)
            .pageFormat("iso-a4")
            .attributes(attributes)
            .build();

        client.getPrinter("MFCL2710DW").print(job)
    }

    @Test
    fun cupsCreateJobPDF() {
        val attributes = hashMapOf("job-attributes" to "sides:keyword:one-sided" )
        val file = File("test.pdf")

        val job = PrintJob.Builder(file.readBytes())
            .jobName("job-name")
            .userName("coden")
            .copies(1)
            .pageFormat("iso-a4")
            .attributes(attributes)
            .build();

        println(client.getPrinter("PDF").print(job).jobId)
    }
}