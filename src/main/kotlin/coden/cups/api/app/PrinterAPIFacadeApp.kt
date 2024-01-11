package coden.cups.api.app

import coden.cups.api.PrinterApiFacadeApplication
import coden.cups.api.PrinterServiceFactory
import coden.cups.api.handlers.*
import coden.cups.api.utils.highlight
import io.javalin.Javalin
import org.slf4j.LoggerFactory
import java.util.*

class PrinterAPIFacadeApp(config: Properties, factory: PrinterServiceFactory) {

    private val app = Javalin.create()
    private val service = factory.createService(config)


    init {
        val log = LoggerFactory.getLogger(PrinterApiFacadeApplication::class.java)
        println(service.javaClass.simpleName)
        log.info("Select PrinterApi: ${highlight("{}")}", service.javaClass.simpleName)
        createEndpoints()
    }

    private fun createEndpoints() = app.apply {
        after(LoggingPreprocessor)
        get("/printers", GetPrintersHandler(service))
        get("/printers/{name}", GetPrinterHandler(service))
        post("/printers/{name}/jobs", CreateJobHandler(service))
        get("/printers/{name}/jobs", GetJobsHandler(service))
        get("/printers/{name}/jobs/{id}", GetJobHandler(service))
        get("/test", TestHandler())
    }

    fun start(port: Int){
        app.start(port)
    }

    fun stop(){
        app.stop()
    }
}