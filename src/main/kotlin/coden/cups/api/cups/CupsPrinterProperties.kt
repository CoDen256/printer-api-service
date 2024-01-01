package coden.cups.api.cups

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("printer.api.cups")
data class CupsPrinterProperties(
    val host: String,
    val port: Int
)