package biped.works.transaction.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import biped.works.compose.collectWithLifecycle
import com.biped.locations.theme.SmallSpacer
import com.biped.locations.theme.TinySpacer
import com.biped.locations.theme.components.LoadingPanel
import com.biped.locations.theme.components.SmallTitle

@Composable
internal fun TransactionScreen(viewModel: TransactionViewModel) {
    var state by remember { mutableStateOf(TransactionInstruction.State()) }

    viewModel.instruction.collectWithLifecycle { instruction ->
        when (instruction) {
            is TransactionInstruction.State -> state = instruction
            is TransactionInstruction.FailedToUpdate -> print("show failure message")
        }
    }

    if (state.isLoading) LoadingPanel()
    else TransactionPanel(state.uiModel)
}

@Composable
fun TransactionPanel(uiModel: TransactionUiModel) {
    Column(
        Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        TopAppbar({}, {})
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiModel.name,
            onValueChange = {})
        SmallSpacer()
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiModel.amount.toString(),
            onValueChange = {})
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

@Preview
@Composable
fun TransactionPane_Preview() {
    TransactionPanel(TransactionUiModel())
}