package com.example.mynotks.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mynotks.R
import com.example.mynotks.ui.theme.alertDialogTitle
import com.example.mynotks.ui.theme.confirmDeleteContainer
import com.example.mynotks.ui.theme.nanumFontfamily
import com.example.mynotks.ui.theme.onBackgroundLight
import com.example.mynotks.ui.theme.onPrimaryLight
import com.example.mynotks.ui.theme.primaryLight

@Composable
fun DeleteAlertDialog(
    shouldShowDialog: MutableState<Boolean>,
    onConfirm: () -> Unit
) {
    if(shouldShowDialog.value) {
        AlertDialog(
            modifier = Modifier
                .shadow(
                    color = onBackgroundLight,
                    offsetX = 8.dp,
                    offsetY = 8.dp,
                    blurRadius = 16.dp
                ),
            title = {
                    Text(
                        text = stringResource(id = R.string.warning),
                        style = TextStyle(
                            fontSize = 48.sp,
                            fontFamily = nanumFontfamily,
                            fontWeight = FontWeight.Bold,
                            color = alertDialogTitle,
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
            },
            text = {
                   Text(
                       text = stringResource(id = R.string.confirm_message),
                       style = TextStyle(
                           fontSize = 32.sp,
                           fontFamily = nanumFontfamily,
                           fontWeight = FontWeight.Normal,
                           color = onPrimaryLight,
                       ),
                       textAlign = TextAlign.Start,
                       modifier = Modifier.fillMaxWidth()
                   )
            },
            containerColor = onBackgroundLight,
            shape = RoundedCornerShape(20.dp),
            onDismissRequest = { shouldShowDialog.value = false },
            dismissButton = {
                            Button(
                                onClick = { shouldShowDialog.value = false },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = primaryLight
                                ),
                                modifier = Modifier.padding(end = 32.dp)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.cancel),
                                    style = TextStyle(
                                        fontSize = 24.sp,
                                        fontFamily = nanumFontfamily,
                                        fontWeight = FontWeight.Normal,
                                        color = onPrimaryLight
                                    ),
                                )
                            }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onConfirm()
                        shouldShowDialog.value = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = confirmDeleteContainer
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.confirm),
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontFamily = nanumFontfamily,
                            fontWeight = FontWeight.Normal,
                            color = onPrimaryLight
                        ),
                    )
                }
             }
        )
    }
}