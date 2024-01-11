package coden.cups.api.service.cups

import java.util.Properties

data class CupsPrinterProperties(
    val host: String,
    val port: Int
){
    constructor(properties: Properties) : this(
        properties.getProperty("cups.host", "localhost"),
        properties.getProperty("cups.port", "631").toInt()
    )
}