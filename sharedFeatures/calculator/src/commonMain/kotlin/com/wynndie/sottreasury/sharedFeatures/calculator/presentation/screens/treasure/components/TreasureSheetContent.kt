package com.wynndie.sottreasury.sharedFeatures.calculator.presentation.screens.treasure.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.wynndie.sottreasury.sharedCore.presentation.theme.sizing
import com.wynndie.sottreasury.sharedCore.presentation.theme.spacing
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.Emissary
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.TreasureValue
import com.wynndie.sottreasury.sharedResources.Res
import com.wynndie.sottreasury.sharedResources.ic_reload
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TreasureSheetContent(
    values: List<TreasureValue>,
    totalValues: Map<Int, Pair<Int, Int>>,
    emissaries: List<Emissary>,
    isEmissaryPickerOpen: Boolean,
    selectedEmissary: Int,
    selectedEmissaryLevel: Int,
    onToggleEmissaryPicker: (Boolean) -> Unit,
    onSelectEmissary: (Int) -> Unit,
    onSelectEmissaryGrade: (Int) -> Unit,
    onReloadData: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        modifier = modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.medium)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            modifier = modifier
                .fillMaxWidth()
        ) {
            values.forEach { currency ->
                val (min, max) = totalValues[currency.currencyId] ?: (0 to 0)
                TreasureCurrencyRow(
                    currency = currency,
                    min = min,
                    max = max
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(
                MaterialTheme.spacing.medium,
                Alignment.CenterHorizontally
            )
        ) {
            EmissarySelector(
                emissaries = emissaries,
                selectedEmissary = selectedEmissary,
                selectedEmissaryGrade = selectedEmissaryLevel,
                isEmissaryPickerOpen = isEmissaryPickerOpen,
                onToggleEmissaryPicker = onToggleEmissaryPicker,
                onSelectEmissary = onSelectEmissary,
                onSelectEmissaryGrade = onSelectEmissaryGrade,
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = onReloadData,
                colors = IconButtonDefaults.iconButtonColors().copy(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.size(MaterialTheme.sizing.extraLarge)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_reload),
                    contentDescription = null
                )
            }
        }
    }
}