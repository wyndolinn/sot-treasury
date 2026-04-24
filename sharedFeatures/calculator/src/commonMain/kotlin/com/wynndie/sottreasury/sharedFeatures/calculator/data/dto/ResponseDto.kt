package com.wynndie.sottreasury.sharedFeatures.calculator.data.dto

import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.CategoryEntity
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.CurrencyEntity
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.EmissaryEntity
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.FactionEntity
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.SubcategoryEntity
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.TreasureEntity
import com.wynndie.sottreasury.sharedFeatures.calculator.data.local.entities.VariantEntity
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDto(
    val majorDimension: String,
    val range: String,
    val values: List<List<String>>
) {
    fun toTreasureEntities() = values.drop(1).map { TreasureEntity.from(it) }
    fun toTreasureValueDtoList() = values.drop(1).map { TreasureValueDto.from(it) }
    fun toCurrencyEntities() = values.drop(1).map { CurrencyEntity.from(it) }
    fun toFactionEntities() = values.drop(1).map { FactionEntity.from(it) }
    fun toCategoryEntities() = values.drop(1).map { CategoryEntity.from(it) }
    fun toSubcategoryEntities() = values.drop(1).map { SubcategoryEntity.from(it) }
    fun toVariantEntities() = values.drop(1).map { VariantEntity.from(it) }
    fun toEmissaryEntityList() = values.drop(1).map { EmissaryEntity.from(it) }
}