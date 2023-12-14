package com.pascal.foodrecipescompose.presentation.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.pascal.foodrecipescompose.R
import com.pascal.foodrecipescompose.data.local.model.ProfileEntity
import com.pascal.foodrecipescompose.presentation.component.ErrorScreen
import com.pascal.foodrecipescompose.presentation.component.LoadingScreen
import com.pascal.foodrecipescompose.presentation.ui.theme.FoodRecipesComposeTheme
import com.pascal.foodrecipescompose.utils.UiState

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.loadProfile()
    }

    val uiState by viewModel.profile.collectAsState()

    Surface(
        modifier = modifier.padding(paddingValues),
        color = MaterialTheme.colorScheme.background
    ) {
        when (uiState) {
            is UiState.Loading -> {
                LoadingScreen()
            }
            is UiState.Error -> {
                val message = (uiState as UiState.Error).message
                ErrorScreen(message = message) {}
            }
            is UiState.Empty -> {
                ErrorScreen(message = stringResource(R.string.empty)) {}
            }
            is UiState.Success -> {
                val data = (uiState as UiState.Success).data
                ProfileContent(
                    itemProfile = data
                )
            }
        }
    }
}

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    itemProfile: ProfileEntity
) {
    var name by remember { mutableStateOf("John Doe") }
    var email by remember { mutableStateOf("john.doe@example.com") }
    var phone by remember { mutableStateOf("123-456-7890") }
    var rating by remember { mutableStateOf(4.5f) }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (image, imageProfile, formField, cardView) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
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
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
        )

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .border(6.dp, Color.White, CircleShape)
                .clip(CircleShape)
                .size(150.dp)
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
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = phone,
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
                Text(text = "Rating: $rating")
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ProfilePreview() {
    FoodRecipesComposeTheme {
        val profile = ProfileEntity(id = 1, name = "name1")
        ProfileContent(
            itemProfile = profile
        )
    }
}