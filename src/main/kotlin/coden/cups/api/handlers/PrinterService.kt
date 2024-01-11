package coden.cups.api.handlers

import coden.cups.api.PrintParams
import coden.cups.api.PrinterService
import io.javalin.http.Context
import io.javalin.http.Handler
import kotlin.random.Random

class GetPrintersHandler(private val service: PrinterService): Handler {
    override fun handle(ctx: Context) {
        service.getPrinters()
            .onFailure { ctx.status(500).json("{\"error\": ${it.message}}") }
            .onSuccess { ctx.json(it) }
    }
}

class GetPrinterHandler(private val service: PrinterService): Handler {
    override fun handle(ctx: Context) {
        val name = runCatching {  ctx.pathParam("name") }
            .onFailure { ctx.status(400).json(InvalidPrinterName()) }
            .getOrNull() ?: return


        service.getPrinter(name)
            .onFailure { ctx.status(404).json(PrinterNotFoundError(name)) }
            .onSuccess { ctx.json(it) }
    }
}

class CreateJobHandler(private val service: PrinterService): Handler {
    override fun handle(ctx: Context) {
        val name = runCatching {  ctx.pathParam("name") }
            .onFailure { ctx.status(400).json(InvalidPrinterName()) }
            .getOrNull() ?: return
        val params = createPrintParams(ctx.queryParamMap())
        val data = ctx.bodyInputStream()

        service.createJob(name, data, params)
            .onFailure { ctx.status(404).json(PrinterNotFoundError(name)) }
            .onSuccess { ctx.json(it) }
    }

    private fun createPrintParams(params: Map<String, List<String>>): PrintParams {
        val result = PrintParams.Builder()
        params.forEach { (key, value) ->
            when(key){
                "copies" -> result.copies(value.firstOrNull()?.toInt() ?: 0)
            }
        }
        return result.build()
    }
}

class GetJobsHandler(private val service: PrinterService): Handler {
    override fun handle(ctx: Context) {
        val name = runCatching {  ctx.pathParam("name") }
            .onFailure { ctx.status(400).json(InvalidPrinterName()) }
            .getOrNull() ?: return

        service.getJobs(name)
            .onFailure { ctx.status(404).json(PrinterNotFoundError(name)) }
            .onSuccess { ctx.json(it) }
    }
}

class GetJobHandler(private val service: PrinterService): Handler {
    override fun handle(ctx: Context) {
        val name = runCatching {  ctx.pathParam("name") }
            .onFailure { ctx.status(400).json(InvalidPrinterName()) }
            .getOrNull() ?: return

        val id = runCatching {  ctx.pathParam("id").toInt() }
            .onFailure { ctx.status(400).json(InvalidJobId()) }
            .getOrNull() ?: return


        service.getJob(name, id)
            .onFailure { ctx.status(404).json(JobNotFoundError(id)) }
            .onSuccess { ctx.json(it) }
    }
}

class TestHandler: Handler {
    override fun handle(ctx: Context) {
        ctx.result("" + Random.nextInt() * Random.nextInt(99999, 99999 * 10))
    }
}

data class JobNotFoundError(val id: Int, val message: String = "Job <$id> does not exist")
data class PrinterNotFoundError(val name: String, val message: String = "Printer <$name> does not exist")
data class InvalidJobId(val message: String = "Invalid Job ID, should be int")
data class InvalidPrinterName(val message: String = "Invalid Printer Name")
