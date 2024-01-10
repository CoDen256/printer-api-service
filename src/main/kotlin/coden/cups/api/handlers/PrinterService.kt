package coden.cups.api.handlers

import coden.cups.api.PrintParams
import coden.cups.api.PrinterService
import io.javalin.http.Context
import io.javalin.http.Handler
import java.io.InputStream
import kotlin.random.Random

class GetPrintersHandler(private val service: PrinterService): Handler {
    override fun handle(ctx: Context) {
        ctx.json(service.getPrinters())
    }
}

class GetPrinterHandler(private val service: PrinterService): Handler {
    override fun handle(ctx: Context) {
        ctx.json(service.getPrinter(ctx.pathParam("name")))
    }
}

class CreateJobHandler(private val service: PrinterService): Handler {
    override fun handle(ctx: Context) {
        val printerName = ctx.pathParam("name")
        val params = createPrintParams(ctx.queryParamMap())
        val data = ctx.bodyInputStream()
        ctx.json(service.createJob(printerName, data, params))
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

class TestHandler: Handler {
    override fun handle(ctx: Context) {
        ctx.result("" + Random.nextInt() * Random.nextInt(99999, 99999 * 10))
    }

}