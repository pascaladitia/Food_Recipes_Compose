package com.pascal.foodrecipescompose.presentation.screen.profile

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapsScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues
) {
    val multiplePermissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    LaunchedEffect(Unit) {
        multiplePermissionState.launchMultiplePermissionRequest()
    }

    val circleData = listOf(
        CircleInfo("Park A", LatLng(37.7749, -122.4194), "This is Park A"),
        CircleInfo("Park B", LatLng(36.7783, -119.4179), "This is Park B"),
        CircleInfo("Park C", LatLng(34.0522, -118.2437), "This is Park C")
    )

    var selectedCircle by remember { mutableStateOf<CircleInfo?>(null) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(36.7783, -119.4179), 11f)
    }

    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        circleData.forEach { circleInfo ->
            Circle(
                center = circleInfo.center,
                clickable = true,
                fillColor = Color.Blue.copy(alpha = 0.3f),
                radius = 5000.0,
                strokeColor = Color.Black,
                strokeWidth = 2f,
                tag = circleInfo,
                onClick = { circle ->
                    selectedCircle = circle.tag as? CircleInfo
                }
            )
        }
    }


    selectedCircle?.let { circle ->
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier.offset(y = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .width(350.dp)
                    .clip(RoundedCornerShape(10))
                    .background(Color.DarkGray)
                    .padding(20.dp)
            ) {
                Text(text = circle.name, style = TextStyle(fontSize = 20.sp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = circle.description, style = TextStyle(fontSize = 16.sp))
            }
        }
    }
}

data class CircleInfo(val name: String, val center: LatLng, val description: String)