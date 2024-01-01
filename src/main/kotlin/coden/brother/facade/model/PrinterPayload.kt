package coden.brother.facade.model

data class PrinterPayload(
    val name: String,
    val description: String,
    val url: String,
    val location: String,
    val isDefault: Boolean
) {}