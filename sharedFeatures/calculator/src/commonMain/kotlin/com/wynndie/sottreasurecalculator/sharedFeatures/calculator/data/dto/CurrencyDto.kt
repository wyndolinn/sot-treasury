package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.dto


data class CurrencyDto(
    val id: Int,
    val name: String,
    val icon: String
) {
    companion object {
        fun from(response: List<String>): CurrencyDto {
            return CurrencyDto(
                id = response[0].toInt(),
                name = response[1],
                icon = response.getOrNull(2) ?: ""
            )
        }
    }
}
