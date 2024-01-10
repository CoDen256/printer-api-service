package coden.cups.api

import coden.cups.api.cups.CupsPrinterApi
import coden.cups.api.cups.CupsPrinterProperties
import coden.cups.api.dummy.DummyPrinterApi
import io.javalin.Javalin
import org.slf4j.LoggerFactory
import java.io.FileInputStream
import java.util.*
import kotlin.random.Random


class PrinterApiFacadeApplication

fun main(args: Array<String>) {
    System.setProperty("log4j.configurationFile", "log4j2.xml");

    val config = loadProperties()
    val api: PrinterApi = getPrinterApi(config)

    val log = LoggerFactory.getLogger(PrinterApiFacadeApplication::class.java)
    log.info("Select PrinterApi: ${highlight("{}")}", api.javaClass.simpleName)

    val app = Javalin.create()
        .get("/printers") { it.json(api.getPrinters()) }
        .get("/test") { it.result("" + Random.nextInt() * Random.nextInt(99999, 99999 * 10)) }
        .start(8080)
}

fun getPrinterApi(config: Properties): PrinterApi {
    return when {
        config.getProperty("dummy", "false").toBoolean() -> DummyPrinterApi
        config.containsKey("cups.host") -> CupsPrinterApi(CupsPrinterProperties(config))
        else -> DummyPrinterApi
    }
}

fun loadProperties(): Properties {
    return FileInputStream("app.config").use {
        Properties().apply { load(it) }
    }
}

fun highlight(string: String): String = "\u001B[32m$string\u001B[0m"