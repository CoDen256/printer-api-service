package coden.cups.api.app

import coden.cups.api.PrinterApiFacadeApplication
import coden.cups.api.PrinterServiceFactory
import coden.cups.api.handlers.CreateJobHandler
import coden.cups.api.handlers.GetPrinterHandler
import coden.cups.api.handlers.GetPrintersHandler
import coden.cups.api.handlers.TestHandler
import coden.cups.api.utils.highlight
import io.javalin.Javalin
import org.slf4j.LoggerFactory
import java.util.*

class PrinterAPIFacadeApp(config: Properties, factory: PrinterServiceFactory) {

    private val app = Javalin.create()
    private val service = factory.createService(config)


    init {
        val log = LoggerFactory.getLogger(PrinterApiFacadeApplication::class.java)
        log.info("Select PrinterApi: ${highlight("{}")}", service.javaClass.simpleName)
    }

    private fun createEndpoints() = app.apply {
        get("/printers", GetPrintersHandler(service))
        get("/printers/{name}", GetPrinterHandler(service))
        post("/printers/{name}/job", CreateJobHandler(service))
        get("/test", TestHandler())
    }

    fun start(port: Int){
        app.start(port)
    }
}