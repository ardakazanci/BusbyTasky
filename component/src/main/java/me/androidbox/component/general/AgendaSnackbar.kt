package me.androidbox.component.general

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.androidbox.component.ui.theme.BusbyTaskyTheme

@Composable
fun AgendaSnackbar(
    snackbarHostState: SnackbarHostState,
    onAction: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = modifier) { data ->

        val isError = true
        val buttonColor = if (isError) {
            ButtonDefaults.textButtonColors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.error
            )
        } else {
            ButtonDefaults.textButtonColors(
                contentColor = MaterialTheme.colorScheme.inversePrimary
            )
        }

        Snackbar(
            modifier = Modifier
                .padding(12.dp),
            action = {
                TextButton(
                    onClick = {
                        onAction()
                    },
                    colors = buttonColor
                ) {
                    Text(text = data.visuals.actionLabel ?: "") }
            },
            dismissAction = {
                onDismiss()
            }
        ) {
            Text(text = data.visuals.message)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewAgendaSnackbar() {
    BusbyTaskyTheme {
        AgendaSnackbar(
            snackbarHostState = SnackbarHostState(),
            onAction = {},
            onDismiss = {}
        )
    }
}