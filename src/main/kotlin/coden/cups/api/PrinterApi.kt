package coden.cups.api

interface PrinterApi {
    fun getPrinters(): List<Printer>
}

data class Printer(
    val name: String?,
    val url: String?,
    val description: String?,
    val location: String?,
    val isDefault: Boolean = false,
)