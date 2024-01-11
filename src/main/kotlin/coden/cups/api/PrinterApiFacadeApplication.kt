package coden.cups.api

import coden.cups.api.app.PrinterAPIFacadeApp
import coden.cups.api.service.cups.CupsPrinterProperties
import coden.cups.api.service.cups.CupsPrinterService
import coden.cups.api.service.dummy.DummyPrinterService
import coden.cups.api.utils.loadProperties
import java.util.*


class PrinterApiFacadeApplication

fun main(args: Array<String>) {
    System.setProperty("log4j.configurationFile", "log4j2.xml");

    val config = loadProperties("app.config")
    val app = PrinterAPIFacadeApp(config, DefaultPrinterServiceFactory())
    app.start(8080)
}

class DefaultPrinterServiceFactory: PrinterServiceFactory {
    override fun createService(config: Properties): PrinterService {
        return when {
            config.getProperty("dummy", "false").toBoolean() -> DummyPrinterService
            config.containsKey("cups.host") -> CupsPrinterService(CupsPrinterProperties(config))
            else -> DummyPrinterService
        }
    }
}