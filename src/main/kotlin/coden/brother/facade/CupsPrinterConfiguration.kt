package coden.brother.facade

import coden.cups.api.PrinterApi
import coden.cups.api.cups.CupsPrinterApi
import coden.cups.api.cups.CupsPrinterProperties
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(CupsPrinterProperties::class)
@ConditionalOnProperty("printer.api.cups", matchIfMissing = true)
class CupsPrinterConfiguration {
    @Bean
    fun printerApi(properties: CupsPrinterProperties): PrinterApi {
        return CupsPrinterApi(properties)
    }
}