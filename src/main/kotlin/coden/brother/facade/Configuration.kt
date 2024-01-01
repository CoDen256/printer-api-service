package coden.brother.facade

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(*[CupsPrinterConfiguration::class, DummyPrinterConfiguration::class])
class Configuration {
}