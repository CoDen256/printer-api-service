package coden.cups.api

import java.io.InputStream

interface PrinterService {
    fun getPrinters(): Result<List<Printer>>
    fun getPrinter(name: String): Result<Printer>
    fun createJob(printerName: String, data: InputStream, params: PrintParams=PrintParams.Builder().build()): Result<PrintJob>
    fun getJobs(printerName: String): Result<List<PrintJob>>
    fun getJob(id: Int): Result<PrintJob>
}

data class Printer(
    val name: String,
    val url: String?,
    val description: String?,
    val location: String?,
    val isDefault: Boolean = false,
)

data class PrintParams(
    val copies: Int,
    val name: String
) {
    data class Builder(
        var copies: Int = 1,
        var name: String = "job"
    ) {
        fun copies(newCopies: Int?) = apply { this.copies = newCopies ?: this.copies }
        fun name(newName: String?) = apply { this.name = newName ?: this.name }
        fun build() = PrintParams(copies, name)
    }
}

data class PrintJob(
    val id: Int,
    val name: String,
    val message: String,
    val success: Boolean,
    val state: String? = null
)