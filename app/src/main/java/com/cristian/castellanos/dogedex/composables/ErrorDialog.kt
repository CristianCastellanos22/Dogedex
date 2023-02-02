package com.cristian.castellanos.dogedex.composables

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.cristian.castellanos.dogedex.R
import com.cristian.castellanos.dogedex.api.ApiResponseStatus

@Composable
fun ErrorDialog(
    messageId: Int,
    onDialogDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {},
        title = {
            Text(text = stringResource(id = R.string.error_dialog_title))
        },
        text = {
            Text(stringResource(id = messageId))
        }, confirmButton = {
            Button(
                onClick = { onDialogDismiss() },
            ) {
                Text(text = stringResource(R.string.try_again))
            }
        }
    )
}