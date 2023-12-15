package com.pascal.foodrecipescompose.presentation.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.pascal.foodrecipescompose.BuildConfig
import com.pascal.foodrecipescompose.R
import com.pascal.foodrecipescompose.utils.HIDE_DIALOG
import com.pascal.foodrecipescompose.utils.createImageFile
import java.util.Objects

@Composable
fun CameraGalleryDialog(
    showDialogCapture: Int,
    onSelect: (Uri) -> Unit
) {
    if (showDialogCapture != HIDE_DIALOG) {
        val context = LocalContext.current
        val file = context.createImageFile()
        val uri = FileProvider.getUriForFile(Objects.requireNonNull(context),
            BuildConfig.APPLICATION_ID + ".provider", file
        )

        val galleryLauncher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
                uri?.let { onSelect(it) }
            }
        val cameraLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
                onSelect(uri)
            }

        AlertDialog(
            title = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    text = stringResource(R.string.select_photo_source)
                )
            },
            text = {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                cameraLauncher.launch(uri)
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            CameraGalleryButton(
                                icon = Icons.Default.PhotoCamera,
                                text = stringResource(R.string.camera)
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = {
                                galleryLauncher.launch("image/*")
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            CameraGalleryButton(
                                icon = Icons.Default.PhotoLibrary,
                                text = stringResource(R.string.gallery)
                            )
                        }
                    }
                }
            },
            onDismissRequest = {},
            confirmButton = {},
            dismissButton = {}
        )
    }
}

@Composable
private fun CameraGalleryButton(icon: ImageVector, text: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = text)
    }
}