@file:OptIn(ExperimentalMaterial3Api::class)

package biped.works.transaction.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import biped.works.compose.collectWithLifecycle
import biped.works.transaction.R
import com.biped.locations.theme.CashTheme
import com.biped.locations.theme.Dimens
import com.biped.locations.theme.SmallSpacer
import com.biped.locations.theme.TinySpacer
import com.biped.locations.theme.components.Loading
import com.biped.locations.theme.components.SmallTitle
import com.biped.locations.theme.components.TextField
import com.favoriteplaces.core.date.formatAsDate

@Composable
internal fun TransactionScreen(
    viewModel: TransactionViewModel,
    onNavigateUp: () -> Unit
) {
    var state by remember { mutableStateOf(TransactionInstruction.State()) }

    viewModel.instruction.collectWithLifecycle { instruction ->
        when (instruction) {
            is TransactionInstruction.State -> state = instruction
            is TransactionInstruction.FailedToUpdate -> print("show failure message")
        }
    }

    if (state.isLoading) {
        Loading()
    } else {
        TransactionPanel(
            uiModel = state.uiModel,
            onNavigateUp = onNavigateUp,
            onSave = viewModel::saveTransaction
        )
    }
}

enum class TransactionType(val title: String) {
    INCOME("Income"),
    EXPENSE("Expense")
}

@Composable
fun TransactionPanel(
    uiModel: TransactionUiModel,
    onSave: (TransactionUiModel) -> Unit = {},
    onNavigateUp: () -> Unit = {}
) {
    var form by remember { mutableStateOf(uiModel) }
    var selectedIndex by remember { mutableStateOf(if (form.amount >= 0) 0 else 1) }

    val options = enumValues<TransactionType>()

    Column(
        Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        TopAppbar(onNavigateUp = onNavigateUp, onSave = { onSave(form) })
        SmallSpacer()
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = Dimens.small)
        ) {
            SingleChoiceSegmentedButtonRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                options.forEachIndexed { index, label ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                        onClick = {
                            if (selectedIndex != index) {
                                selectedIndex = index
                                form = form.invertAmount()
                            }
                        },
                        selected = index == selectedIndex
                    ) {
                        Text(label.title)
                    }
                }
            }
            SmallSpacer()
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = form.name,
                label = { Text(text = stringResource(R.string.transaction_name)) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                onValueChange = { form = form.copy(name = it) })
            SmallSpacer()
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = form.description,
                label = { Text(stringResource(R.string.transaction_description)) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                onValueChange = { form = form.copy(description = it) })
            SmallSpacer()
            Row {
                CurrencySelector(
                    selected = uiModel.currency,
                    onSelect = { code -> form = form.copy(currency = code) }
                )
                TinySpacer()
                TextField(
                    value = form.formatAmount(),
                    onValueChange = { form = form.setAmount(it) },
                    label = { Text("Amount") })
            }
            SmallSpacer()
            DatePickerTextField(
                initialTime = form.dueEpoch,
                onDateSelect = {
                    form = form.copy(due = it.formatAsDate())
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppbar(
    onNavigateUp: () -> Unit, onSave: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = { Text(text = "Transaction") },
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "")
            }
        },
        actions = {
            IconButton(onClick = onSave) {
                SmallTitle(text = "Save", color = MaterialTheme.colorScheme.tertiary)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TransactionPane_Preview() {
    CashTheme {
        TransactionPanel(
            TransactionUiModel(
                id = "24325dfe",
                name = "Car Rent",
                description = "For the trip from x to y",
                due = "2024-01-01",
                amount = 320.00,
                currency = "R$"
            )
        )
    }

}