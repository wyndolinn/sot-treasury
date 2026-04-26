package com.wynndie.sottreasury.sharedFeatures.calculator.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wynndie.sottreasury.sharedCore.presentation.theme.spacing
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.TreasureValue

@Composable
fun TreasureLayout(
    name: String,
    amount: Int,
    values: List<TreasureValue>,
    onChangeAmount: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraExtraSmall),
        modifier = modifier
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleSmall
        )

        Row {
            Column(modifier = Modifier.weight(1f)) {
                values.forEach { currency ->
                    TreasureValueLayout(currency)
                }
            }

            AmountChanger(
                amount = amount,
                onIncrement = { onChangeAmount(amount + 1) },
                onDecrement = { onChangeAmount(amount - 1) }
            )
        }
    }
}