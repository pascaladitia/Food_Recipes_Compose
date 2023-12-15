package com.pascal.foodrecipescompose.presentation.screen.profile

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.pascal.foodrecipescompose.R
import com.pascal.foodrecipescompose.data.local.model.ProfileEntity
import com.pascal.foodrecipescompose.presentation.component.CameraGalleryDialog
import com.pascal.foodrecipescompose.presentation.component.ErrorScreen
import com.pascal.foodrecipescompose.presentation.component.LoadingScreen
import com.pascal.foodrecipescompose.presentation.ui.theme.FoodRecipesComposeTheme
import com.pascal.foodrecipescompose.utils.HIDE_DIALOG
import com.pascal.foodrecipescompose.utils.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.loadProfile()
    }

    val coroutineScope = rememberCoroutineScope()
    var editMode by remember { mutableStateOf(false) }

    val uiState by viewModel.profile.collectAsState()

    Surface(
        modifier = modifier.padding(paddingValues),
        color = MaterialTheme.colorScheme.background
    ) {
        AnimatedVisibility(
            visible = uiState !is UiState.Loading,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            Crossfade(targetState = uiState, label = "") { state ->
                when (state) {
                    is UiState.Loading -> {
                        LoadingScreen()
                    }
                    is UiState.Error -> {
                        val message = state.message
                        ErrorScreen(message = message) {}
                    }
                    is UiState.Empty -> {
                        ProfileEditContent(
                            itemProfile = ProfileEntity(),
                            onSave = {
                                viewModel.addProfile(it)
                                editMode = false

                                coroutineScope.launch {
                                    viewModel.loadProfile()
                                }
                            }
                        )
                    }
                    is UiState.Success -> {
                        val data = state.data
                        if (editMode) {
                            ProfileEditContent(
                                itemProfile = data,
                                onSave = {
                                    viewModel.addProfile(it)
                                    editMode = false

                                    coroutineScope.launch {
                                        viewModel.loadProfile()
                                    }
                                }
                            )
                        } else {
                            ProfileContent(
                                itemProfile = data,
                                onEdit = {
                                    editMode = true
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    itemProfile: ProfileEntity,
    onEdit: () -> Unit
) {
    var name by remember { mutableStateOf(itemProfile.name) }
    var email by remember { mutableStateOf(itemProfile.email) }
    var phone by remember { mutableStateOf(itemProfile.phone) }
    var address by remember { mutableStateOf(itemProfile.address) }
    var imageUri by remember { mutableStateOf<Uri?>(Uri.parse(itemProfile.imagePath)) }
    var imageProfileUri by remember { mutableStateOf<Uri?>(Uri.parse(itemProfile.imageProfilePath)) }

    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (image, imageProfile, formField, cardView) = createRefs()

        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(data = imageUri)
                    .error(R.drawable.no_thumbnail)
                    .apply { crossfade(true) }
                    .build()
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Box(
            modifier = Modifier
                .constrainAs(cardView) {
                    top.linkTo(image.top, margin = 180.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(Color.White)
        )

        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(data = imageProfileUri)
                    .error(R.drawable.no_profile)
                    .apply { crossfade(true) }
                    .build()
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .border(6.dp, Color.White, CircleShape)
                .clip(CircleShape)
                .size(150.dp)
                .background(MaterialTheme.colorScheme.background, CircleShape)
                .constrainAs(imageProfile) {
                    centerHorizontallyTo(parent)
                    centerAround(cardView.top)
                }
        )

        Column(
            modifier = Modifier
                .padding(24.dp)
                .constrainAs(formField) {
                    top.linkTo(imageProfile.bottom, margin = 24.dp)
                }
        ) {
            OutlinedTextField(
                value = itemProfile.name.toString(),
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                singleLine = true
            )
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = itemProfile.email.toString(),
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                singleLine = true
            )
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value =itemProfile.phone.toString(),
                onValueChange = { phone = it },
                label = { Text("Phone") },
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                singleLine = true
            )
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Address: $address")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onEdit()
                }
            ) {
                Text(text = "Edit")
            }
        }
    }
}

@Composable
fun ProfileEditContent(
    modifier: Modifier = Modifier,
    itemProfile: ProfileEntity,
    onSave: (ProfileEntity) -> Unit
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf(itemProfile.name) }
    var email by remember { mutableStateOf(itemProfile.email) }
    var phone by remember { mutableStateOf(itemProfile.phone) }
    var address by remember { mutableStateOf(itemProfile.address) }

    var showDialogCapture by remember { mutableIntStateOf(HIDE_DIALOG) }
    var imageUri by remember { mutableStateOf<Uri?>(Uri.parse(itemProfile.imagePath)) }
    var imageProfileUri by remember { mutableStateOf<Uri?>(Uri.parse(itemProfile.imageProfilePath)) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Success", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (image, imageProfile, formField, cardView) = createRefs()

        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(data = imageUri ?: R.drawable.no_thumbnail)
                    .error(R.drawable.no_thumbnail)
                    .apply { crossfade(true) }
                    .build()
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .clickable {
                    val permissionCheckResult =
                        ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                        showDialogCapture = 1
                    } else {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                }
        )

        Box(
            modifier = Modifier
                .constrainAs(cardView) {
                    top.linkTo(image.top, margin = 180.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(Color.White)
        )

        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(data = imageProfileUri ?: R.drawable.no_profile)
                    .error(R.drawable.no_profile)
                    .apply { crossfade(true) }
                    .build()
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .border(6.dp, Color.White, CircleShape)
                .clip(CircleShape)
                .size(150.dp)
                .background(MaterialTheme.colorScheme.background, CircleShape)
                .constrainAs(imageProfile) {
                    centerHorizontallyTo(parent)
                    centerAround(cardView.top)
                }
                .clickable {
                    val permissionCheckResult =
                        ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                        showDialogCapture = 2
                    } else {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                }
        )

        Column(
            modifier = Modifier
                .padding(24.dp)
                .constrainAs(formField) {
                    top.linkTo(imageProfile.bottom, margin = 24.dp)
                }
        ) {
            OutlinedTextField(
                value = name.toString(),
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email.toString(),
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = phone.toString(),
                onValueChange = { phone = it },
                label = { Text("Phone") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Address: $address")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    val profile = ProfileEntity(
                        id = 1,
                        name = name,
                        imagePath = imageUri.toString(),
                        imageProfilePath = imageProfileUri.toString(),
                        email = email,
                        phone = phone,
                        address = address
                    )
                    onSave(profile)
                }
            ) {
                Text(text = "Save")
            }
        }

        CameraGalleryDialog(
            showDialogCapture = showDialogCapture,
            onSelect = { uri ->
                if (showDialogCapture == 1) {
                    imageUri = uri
                } else if (showDialogCapture == 2) {
                    imageProfileUri = uri
                }
                showDialogCapture = HIDE_DIALOG
            },
            onDismiss = {
                showDialogCapture = HIDE_DIALOG
            }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun ProfilePreview() {
    FoodRecipesComposeTheme {
        val profile = ProfileEntity(id = 1, name = "name1")
        ProfileContent(
            itemProfile = profile,
            onEdit = {}
        )
    }
}