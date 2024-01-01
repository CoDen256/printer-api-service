package coden.brother.facade

import coden.cups.api.PrinterApi
import coden.cups.api.cups.CupsPrinterProperties
import coden.cups.api.dummy.DummyPrinterApi
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(CupsPrinterProperties::class)
@ConditionalOnProperty("printer.api.dummy")
class DummyPrinterConfiguration {
    @Bean
    fun printerApi(): PrinterApi {
        return DummyPrinterApi()
    }
}