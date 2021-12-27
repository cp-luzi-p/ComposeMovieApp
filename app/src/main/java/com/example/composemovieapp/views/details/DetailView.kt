package com.example.composemovieapp.views.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.example.composemovieapp.R
import com.example.composemovieapp.components.CastItemList
import com.example.composemovieapp.data.Status
import com.example.composemovieapp.model.Cast
import com.example.composemovieapp.model.MovieCast
import com.example.composemovieapp.model.MovieDetail
import com.example.composemovieapp.ui.theme.blue
import com.example.composemovieapp.ui.theme.dark_gray
import com.example.composemovieapp.ui.theme.white
import com.example.composemovieapp.utils.AppConstants
import com.example.composemovieapp.viewModel.AppViewModel

@Composable
fun DetailView(
    viewModel: AppViewModel,
    back: () -> Unit,
    movieId: String
) {

    var movieDetails: MovieDetail? = null

    viewModel.getMoviesDetails("d6df0466d78f7e3c54d746506bb86334", movieId = movieId)
    val result = viewModel.moviesDetails.collectAsState().value

    when (result.status) {
        Status.LOADING -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        Status.SUCCESS -> {
            result.data?.let {
                movieDetails = it
                viewModel.getMoviesCast("d6df0466d78f7e3c54d746506bb86334", movieId = movieId)
            }
        }
        Status.ERROR -> {
            Text(
                text = "${result.message}",
                modifier = Modifier.wrapContentSize(Alignment.Center)
            )
        }
    }

    val cast = viewModel.castingMovie.collectAsState().value
    when (cast.status) {
        Status.SUCCESS -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxHeight()
                ) {
                    item {
                        MovieImageSection(movieDetail = movieDetails, back)
                        MovieDetailsSection(movieDetail = movieDetails)
                        MovieDescriptionSection(moviesDetails = movieDetails)
                        MovieCastSection(cast = cast.data!!)
                    }
                }
            }
        }
        Status.ERROR -> {
            Text(
                text = "${result.message}",
                modifier = Modifier.wrapContentSize(Alignment.Center)
            )
        }
    }
}

@Composable
fun MovieDetailsSection(movieDetail: MovieDetail?) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .absolutePadding(
                top = 40.dp,
                left = 20.dp,
                right = 20.dp
            )
    ) {


        val (
            tvMovieName, rowLike, tvDate,
            tvVotingRatio, tvGenre, rowLang
        ) = createRefs()

        Text(
            text = movieDetail?.original_title.toString(),
            modifier = Modifier.constrainAs(tvMovieName) {},
            color = MaterialTheme.colors.onBackground,
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.pt_sans_bold))
            ),
            fontSize = 23.sp
        )

        Row(
            modifier = Modifier
                .padding(top = 20.dp)
                .constrainAs(rowLike) {
                    start.linkTo(parent.start)
                    top.linkTo(tvMovieName.bottom)
                }
        ) {
            Text(
                text = "Rating: ",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = MaterialTheme.colors.onBackground,
                    fontFamily = FontFamily(Font(R.font.pt_sans_bold))
                )
            )

            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = "${movieDetail?.vote_average}/10",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = dark_gray,
                    fontFamily = FontFamily(Font(R.font.pt_sans_regular))
                )
            )

        }


        Row(modifier = Modifier
            .padding(top = 5.dp)
            .constrainAs(tvDate) {
                top.linkTo(rowLike.bottom)
            }
        ) {

            Text(
                text = "Release Date: ",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = MaterialTheme.colors.onBackground,
                    fontFamily = FontFamily(Font(R.font.pt_sans_bold))
                )
            )

            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = "${movieDetail?.release_date.toString()}",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = dark_gray,
                    fontFamily = FontFamily(Font(R.font.pt_sans_regular))
                )
            )
        }

        Text(
            modifier = Modifier
                .padding(top = 20.dp)
                .constrainAs(tvVotingRatio) {
                    end.linkTo(parent.end)
                    top.linkTo(tvMovieName.bottom)
                },
            text = "${movieDetail?.vote_count} Voting",
            style = TextStyle(
                color = dark_gray,
                fontFamily = FontFamily(Font(R.font.pt_sans_regular))
            ),
            fontSize = 18.sp,
        )

        Row(
            modifier = Modifier
                .padding(
                    top = 5.dp,
                )
                .constrainAs(tvGenre) {
                    top.linkTo(tvDate.bottom)
                },
        ) {
            Text(
                text = "Duration: ",
                style = TextStyle(
                    color = MaterialTheme.colors.onBackground,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.pt_sans_bold))
                ),
                fontSize = 18.sp,
            )
            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = "${movieDetail?.runtime} Mins",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = blue,
                    fontFamily = FontFamily(Font(R.font.pt_sans_regular))
                )
            )
        }

        Row(
            modifier = Modifier
                .padding(top = 5.dp)
                .constrainAs(rowLang) {
                    top.linkTo(tvGenre.bottom)
                }
        ) {
            Text(
                text = "Language: ",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground,
                    fontFamily = FontFamily(Font(R.font.pt_sans_bold))
                )
            )
            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = "English",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = dark_gray,
                    fontFamily = FontFamily(Font(R.font.pt_sans_regular))
                )
            )
        }
    }

}

@Composable
fun MovieImageSection(movieDetail: MovieDetail?, back: () -> Unit) {
    ConstraintLayout {
        Image(
            painter = rememberImagePainter(AppConstants.IMAGE_BASEURL + movieDetail?.backdrop_path),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(
                    shape = RoundedCornerShape(
                        bottomEnd = 30.dp,
                        bottomStart = 30.dp
                    )
                )
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = null,
            tint = white,
            modifier = Modifier
                .padding(
                    start = 20.dp,
                    top = 20.dp
                )
                .clickable {
                    back.invoke()
                }
        )

    }
}

@Composable
fun MovieDescriptionSection(moviesDetails: MovieDetail?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .absolutePadding(
                top = 40.dp,
                left = 20.dp,
                right = 20.dp,
            )
    ) {
        Text(
            text = "Movie Description ",
            style = TextStyle(
                fontSize = 19.sp,
                color = MaterialTheme.colors.onBackground,
                fontFamily = FontFamily(Font(R.font.pt_sans_bold))
            )
        )
        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = moviesDetails?.overView.toString(),
            style = TextStyle(
                fontSize = 16.sp,
                color = dark_gray,
                fontFamily = FontFamily(Font(R.font.pt_sans_regular))
            ),
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
fun MovieCastSection(cast: MovieCast) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .absolutePadding(
                top = 20.dp,
                left = 20.dp,
                right = 20.dp
            )
    ) {

        val (tvCast,castDisplay) = createRefs()

        Text(text = "Cast",
            style = TextStyle(
                fontSize = 19.sp,
                color = dark_gray,
                fontFamily = FontFamily(Font(R.font.pt_sans_bold))
            ),
            modifier = Modifier.constrainAs(tvCast) {}
        )

        LazyRow(modifier = Modifier
            .padding(top = 10.dp, bottom = 16.dp)
            .constrainAs(castDisplay) {
                top.linkTo(tvCast.bottom)
            }
        ) {
            itemsIndexed(cast.cast) { index, item: Cast ->
                CastItemList(item)
            }
        }
    }

}