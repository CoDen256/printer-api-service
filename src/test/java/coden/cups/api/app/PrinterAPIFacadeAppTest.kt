package coden.cups.api.app

import coden.cups.api.DefaultPrinterServiceFactory
import coden.cups.api.utils.loadResourceProperties
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Path
import java.util.*

@Disabled
class PrinterAPIFacadeAppTest {


    private val factory = DefaultPrinterServiceFactory()

    @Test
    fun test() {
        val app = PrinterAPIFacadeApp(Properties().apply { setProperty("dummy", "true") }, factory)
        app.start(8080)
        val client = HttpClient.newHttpClient()
        val request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/test"))
            .GET()
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        println(response.body())
        app.stop()
    }

    @Test
    fun createJobTest() {
        val app = PrinterAPIFacadeApp(Properties().apply { setProperty("dummy", "true") }, factory)
        app.start(8080)
        val client = HttpClient.newHttpClient()
        val request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/printers/DummyDumDum/job"))
            .POST(HttpRequest.BodyPublishers.ofFile(Path.of("test.pdf")))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        println(response.body())
        app.stop()
    }

    @Test
    fun createJobITPDFDriver() {
        val app = PrinterAPIFacadeApp(loadResourceProperties("/app.config"), factory)
        app.start(8080)
        val client = HttpClient.newHttpClient()
        val request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/printers/PDF/job"))
            .POST(HttpRequest.BodyPublishers.ofFile(Path.of("test.pdf")))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        println(response.body())
    }

    @Test
    fun createJobITBrotherDriver() {
        val app = PrinterAPIFacadeApp(loadResourceProperties("/app.config"), factory)
        app.start(8080)
        val client = HttpClient.newHttpClient()
        val request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/printers/MFCL2710DW/job"))
            .POST(HttpRequest.BodyPublishers.ofFile(Path.of("test.pdf")))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        println(response.body())
    }
}