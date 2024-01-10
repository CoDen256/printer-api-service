package coden.cups.api

import coden.cups.api.cups.CupsPrinterService
import coden.cups.api.cups.CupsPrinterProperties
import coden.cups.api.dummy.DummyPrinterService
import coden.cups.api.handlers.GetPrinterHandler
import coden.cups.api.handlers.GetPrintersHandler
import coden.cups.api.handlers.CreateJobHandler
import coden.cups.api.handlers.TestHandler
import io.javalin.Javalin
import org.slf4j.LoggerFactory
import java.io.FileInputStream
import java.util.*


class PrinterApiFacadeApplication

fun main(args: Array<String>) {
    System.setProperty("log4j.configurationFile", "log4j2.xml");

    val config = loadProperties()
    val api: PrinterService = loadPrinterService(config)

    val log = LoggerFactory.getLogger(PrinterApiFacadeApplication::class.java)
    log.info("Select PrinterApi: ${highlight("{}")}", api.javaClass.simpleName)

    Javalin.create()
        .get("/printers", GetPrintersHandler(api))
        .get("/printers/{name}", GetPrinterHandler(api))
        .post("/printers/{name}/job", CreateJobHandler(api))
        .get("/test", TestHandler())
        .start(8080)
}

fun loadPrinterService(config: Properties): PrinterService {
    return when {
        config.getProperty("dummy", "false").toBoolean() -> DummyPrinterService
        config.containsKey("cups.host") -> CupsPrinterService(CupsPrinterProperties(config))
        else -> DummyPrinterService
    }
}

fun loadProperties(): Properties {
    return FileInputStream("app.config").use {
        Properties().apply { load(it) }
    }
}

fun highlight(string: String): String = "\u001B[32m$string\u001B[0m"