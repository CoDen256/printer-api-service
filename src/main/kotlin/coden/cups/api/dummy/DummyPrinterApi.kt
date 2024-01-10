package coden.cups.api.dummy

import coden.cups.api.Printer
import coden.cups.api.PrinterApi

object DummyPrinterApi : PrinterApi {
    override fun getPrinters(): List<Printer> {
        return listOf(
            Printer("DummyDumDum", null, null, null)
        )
    }
}