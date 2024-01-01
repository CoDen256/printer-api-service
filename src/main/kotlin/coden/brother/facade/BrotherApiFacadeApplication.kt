package coden.brother.facade

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BrotherApiFacadeApplication

fun main(args: Array<String>) {
	runApplication<BrotherApiFacadeApplication>(*args)
}
