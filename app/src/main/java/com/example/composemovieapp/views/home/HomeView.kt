package com.example.composemovieapp.views.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import com.example.composemovieapp.R
import com.example.composemovieapp.components.NowPlayingMovieList
import com.example.composemovieapp.components.UpComingMoviesListItem
import com.example.composemovieapp.data.Status
import com.example.composemovieapp.model.Movie
import com.example.composemovieapp.navigation.Actions
import com.example.composemovieapp.ui.theme.dark_gray
import com.example.composemovieapp.viewModel.AppViewModel

@ExperimentalCoilApi
@Composable
fun HomeView(
    viewModel: AppViewModel,
    actions: Actions
) {

    viewModel.getPlayNowMovies("d6df0466d78f7e3c54d746506bb86334")
    val playMovies = arrayListOf<Movie>()

    val playNowResult = viewModel.playNowMovies.collectAsState().value
    when (playNowResult.status) {
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
            playNowResult.data?.results?.let {
                playMovies.addAll(it)
                viewModel.getUpcomingMovies("d6df0466d78f7e3c54d746506bb86334")
            }
        }
        Status.EMPTY -> {
            Text(
                text = "No Movies Found",
                modifier = Modifier.wrapContentSize(Alignment.Center),
                color = MaterialTheme.colors.onBackground
            )
        }

        Status.ERROR -> {
            Text(
                text = "${playNowResult.message}",
                modifier = Modifier.wrapContentSize(Alignment.Center),
                color = MaterialTheme.colors.onBackground
            )
        }
    }

    val upcomingMovies = viewModel.upComeMovie.collectAsState().value
    when (upcomingMovies.status) {
        Status.SUCCESS -> {
            upcomingMovies.data?.results?.let { upcomingMoviesData ->
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxHeight()
                            .absolutePadding(
                                top = 20.dp,
                                left = 20.dp,
                                right = 20.dp,
                            )
                    ) {
                        item {
                            Text(
                                text = "What are you looking For?",
                                color = MaterialTheme.colors.onBackground,
                                style = TextStyle(
                                    color = MaterialTheme.colors.onBackground,
                                    fontFamily = FontFamily(Font(R.font.pt_sans_bold))
                                ),
                                fontSize = 22.sp
                            )
                            NowPlayingMovieList(moviesList = playMovies, action = actions)
                        }

                        item {
                            Spacer(modifier = Modifier.padding(top = 40.dp))
                            Text(
                                text = "Upcoming Movies",
                                style = TextStyle(
                                    fontSize = 17.sp,
                                    fontFamily = FontFamily(Font(R.font.pt_sans_bold)),
                                    color = dark_gray
                                )
                            )
                        }

                        itemsIndexed(upcomingMoviesData) { index: Int, item: Movie ->
                            Spacer(modifier = Modifier.padding(top = 10.dp))
                            UpComingMoviesListItem(model = item, action = actions)


                        }
                    }
                }
            }
        }

        Status.ERROR -> {
            Text(
                text = "No Movies Found",
                modifier = Modifier.wrapContentSize(Alignment.Center),
                color = MaterialTheme.colors.onBackground
            )
        }
    }
}