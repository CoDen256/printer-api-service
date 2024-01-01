package coden.cups.api.cups

import coden.cups.api.PrinterApi
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(PrinterApiProperties::class)
class CupsPrinterConfiguration {
    @Bean
    @ConditionalOnProperty("printer.api", "cups")
    fun printerApi(properties: PrinterApiProperties): PrinterApi {
        return CupsPrinterApi(properties.host, properties.port)
    }
}