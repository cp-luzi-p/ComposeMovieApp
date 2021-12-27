package com.example.composemovieapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.composemovieapp.R
import com.example.composemovieapp.model.Movie
import com.example.composemovieapp.navigation.Actions
import com.example.composemovieapp.ui.theme.dark_gray
import com.example.composemovieapp.ui.theme.red
import com.example.composemovieapp.utils.AppConstants

@ExperimentalCoilApi
@Composable
fun NowPlayingMovieListItem(
    moviesList: Movie,
    action: Actions
) {

    Column(modifier = Modifier
        .padding(end = 10.dp)
        .width(130.dp)
        .clickable { action.actionDetails.invoke(moviesList.id) }
    ) {
        Image(
            painter = rememberImagePainter(AppConstants.IMAGE_BASEURL + moviesList.poster_path),
            contentDescription = null,
            modifier = Modifier
                .height(160.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.FillBounds,
        )
        Spacer(modifier = Modifier.padding(top = 5.dp))
        Text(
            modifier = Modifier.padding(start = 5.dp, end = 5.dp),
            text = moviesList.original_title,
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.pt_sans_regular)),
                fontSize = 18.sp,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.padding(top = 10.dp))
        Row {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_favorite),
                contentDescription = null,
                tint = red,
                modifier = Modifier.padding(top = 1.dp, start = 5.dp)
            )
            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = "${moviesList.vote_average}/10",
                style = TextStyle(
                    fontSize = 15.sp,
                    color = dark_gray,
                    fontFamily = FontFamily(Font(R.font.pt_sans_regular))
                )
            )
        }

    }
}