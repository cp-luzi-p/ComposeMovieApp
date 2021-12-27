package com.example.composemovieapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import com.example.composemovieapp.R
import coil.compose.rememberImagePainter
import com.example.composemovieapp.model.Cast
import com.example.composemovieapp.utils.AppConstants

@ExperimentalCoilApi
@Composable
fun CastItemList(model: Cast) {

    Column(
        modifier = Modifier
            .padding(end = 10.dp)
            .width(100.dp)
    ) {
        Image(
            painter = model.profile_path?.let {
                rememberImagePainter(AppConstants.IMAGE_BASEURL + model.profile_path)
            } ?: painterResource(id = R.drawable.ic_wallpaper),
            contentDescription = null,
            modifier = Modifier
                .width(100.dp)
                .height(130.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.FillBounds,
        )
        Spacer(modifier = Modifier.padding(top = 5.dp))
        Text(
            text = model.name,
            style = TextStyle(
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

    }
}