package com.example.composemovieapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
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
fun UpComingMoviesListItem(
    model: Movie,
    action: Actions
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp, end = 10.dp)
            .clickable {
                action.actionDetails.invoke(model.id)
            }
    ) {

        val (image, title, description, like, comment) = createRefs()

        Image(
            painter = rememberImagePainter(AppConstants.IMAGE_BASEURL + model.poster_path),
            contentDescription = null,
            modifier = Modifier
                .height(140.dp)
                .width(100.dp)
                .clip(RoundedCornerShape(10.dp))
                .constrainAs(image) {},
            contentScale = ContentScale.FillBounds,
        )

        Text(
            text = model.original_title,
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.pt_sans_regular)),
                color = MaterialTheme.colors.onBackground
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(start = 10.dp, top = 5.dp)
                .constrainAs(title) {
                    width = Dimension.fillToConstraints
                    start.linkTo(image.end)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = model.overview,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.pt_sans_regular)),
                textAlign = TextAlign.Justify
            ),
            maxLines = 4,
            modifier = Modifier
                .padding(start = 10.dp, top = 5.dp)
                .constrainAs(description) {
                    width = Dimension.fillToConstraints
                    start.linkTo(image.end)
                    top.linkTo(title.bottom)
                    end.linkTo(parent.end)
                }
        )

        Row(
            modifier = Modifier
                .padding(start = 10.dp)
                .constrainAs(like) {
                    start.linkTo(image.end)
                    bottom.linkTo(image.bottom)
                }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_favorite),
                contentDescription = null,
                tint = red,
                modifier = Modifier.padding(top = 1.dp)
            )

            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = "${model.vote_average}/10",
                style = TextStyle(
                    fontSize = 15.sp,
                    color = dark_gray,
                    fontFamily = FontFamily(Font(R.font.pt_sans_regular))
                )
            )
        }

        Row(
            modifier = Modifier
                .padding(start = 10.dp)
                .constrainAs(comment) {
                    start.linkTo(like.end)
                    bottom.linkTo(image.bottom)
                }
        ) {

            Icon(
                painter = painterResource(id = R.drawable.ic_comment),
                contentDescription = null,
                modifier = Modifier.padding(top = 1.dp)
            )
            Text(
                text = "2k",
                modifier = Modifier.padding(start = 5.dp),
                style = TextStyle(
                    fontSize = 15.sp,
                    color = dark_gray,
                    fontFamily = FontFamily(Font(R.font.pt_sans_regular))
                )
            )

        }

    }
}