package coden.cups.api

import java.util.Properties

interface PrinterServiceFactory {
    fun createService(config: Properties): PrinterService
}