package com.wynndie.sottreasurecalculator.sharedFeatures.calculator.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ResponseDto(
    val majorDimension: String,
    val range: String,
    val values: List<List<String>>
) {
    fun toTreasureDtoList() = values.drop(1).map { TreasureDto.from(it) }
    fun toTreasureValueDtoList() = values.drop(1).map { TreasureValueDto.from(it) }
    fun toFactionDtoList() = values.drop(1).map { FactionDto.from(it) }
    fun toCategoryDtoList() = values.drop(1).map { CategoryDto.from(it) }
    fun toSubcategoryDtoList() = values.drop(1).map { SubcategoryDto.from(it) }
    fun toCurrencyDtoList() = values.drop(1).map { CurrencyDto.from(it) }
    fun toEmissaryDtoList() = values.drop(1).map { EmissaryDto.from(it) }
}