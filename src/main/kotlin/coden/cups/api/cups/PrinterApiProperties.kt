package coden.cups.api.cups

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("printer.api")
data class PrinterApiProperties(
    val host: String,
    val port: Int
)