package com.wynndie.sottreasury.sharedFeatures.calculator.presentation.screens.treasure.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.wynndie.sottreasury.sharedCore.presentation.theme.sizes
import com.wynndie.sottreasury.sharedCore.presentation.theme.spacing
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.Emissary
import com.wynndie.sottreasury.sharedFeatures.calculator.domain.models.TreasureValue
import com.wynndie.sottreasury.sharedResources.Res
import com.wynndie.sottreasury.sharedResources.clear_amounts
import com.wynndie.sottreasury.sharedResources.ic_clear
import com.wynndie.sottreasury.sharedResources.ic_delete
import com.wynndie.sottreasury.sharedResources.ic_menu
import com.wynndie.sottreasury.sharedResources.ic_reload
import com.wynndie.sottreasury.sharedResources.load_data
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
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
    onClearAmounts: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large),
        modifier = modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.medium)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraExtraSmall),
            modifier = modifier.fillMaxWidth()
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

            Box {
                IconButton(
                    onClick = { expanded = !expanded },
                    shape = MaterialTheme.shapes.medium,
                    colors = IconButtonDefaults.iconButtonColors().copy(
                        containerColor = if (expanded) {
                            MaterialTheme.colorScheme.primary
                        } else MaterialTheme.colorScheme.primaryContainer,
                        contentColor = if (expanded) {
                            MaterialTheme.colorScheme.onPrimary
                        } else MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.size(MaterialTheme.sizes.extraLarge)
                ) {
                    Icon(
                        painter = if (expanded) {
                            painterResource(Res.drawable.ic_clear)
                        } else painterResource(Res.drawable.ic_menu),
                        contentDescription = null,
                        modifier = Modifier.size(MaterialTheme.sizes.small)
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    containerColor = Color.Transparent,
                    tonalElevation = 0.dp,
                    shadowElevation = 0.dp
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(Res.string.load_data),
                                    style = MaterialTheme.typography.titleSmall
                                )
                            },
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(Res.drawable.ic_reload),
                                    contentDescription = null
                                )
                            },
                            contentPadding = PaddingValues(
                                horizontal = MaterialTheme.spacing.medium,
                                vertical = MaterialTheme.spacing.extraSmall
                            ),
                            colors = MenuDefaults.itemColors().copy(
                                textColor = MaterialTheme.colorScheme.primary,
                                trailingIconColor = MaterialTheme.colorScheme.primary
                            ),
                            onClick = {
                                onReloadData()
                                expanded = false
                            },
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.medium)
                                .background(MaterialTheme.colorScheme.primaryContainer)
                        )

                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(Res.string.clear_amounts),
                                    style = MaterialTheme.typography.titleSmall
                                )
                            },
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(Res.drawable.ic_delete),
                                    contentDescription = null
                                )
                            },
                            contentPadding = PaddingValues(
                                horizontal = MaterialTheme.spacing.medium,
                                vertical = MaterialTheme.spacing.extraSmall
                            ),
                            colors = MenuDefaults.itemColors().copy(
                                textColor = MaterialTheme.colorScheme.primary,
                                trailingIconColor = MaterialTheme.colorScheme.primary
                            ),
                            onClick = {
                                onClearAmounts()
                                expanded = false
                            },
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.medium)
                                .background(MaterialTheme.colorScheme.primaryContainer)
                        )
                    }
                }
            }
        }
    }
}