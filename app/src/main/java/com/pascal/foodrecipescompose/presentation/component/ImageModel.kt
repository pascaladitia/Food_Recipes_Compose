package com.pascal.foodrecipescompose.presentation.component

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import coil.request.ImageRequest
import com.pascal.foodrecipescompose.R

@Composable
fun ImageModel(
    context: Context,
    url: String? = ""
): ImageRequest {
    val model = remember {
        ImageRequest.Builder(context)
            .data(url)
            .size(1024)
            .crossfade(true)
            .placeholder(R.drawable.loading)
            .error(R.drawable.logo)
            .build()
    }
    return model
}