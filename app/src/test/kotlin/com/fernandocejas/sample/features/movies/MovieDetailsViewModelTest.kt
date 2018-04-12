/**
 * Copyright (C) 2018 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fernandocejas.sample.features.movies

import com.fernandocejas.sample.AndroidTest
import com.fernandocejas.sample.framework.functional.Either.Right
import com.fernandocejas.sample.navigation.Navigator
import com.nhaarman.mockito_kotlin.given
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class MovieDetailsViewModelTest : AndroidTest() {

    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private lateinit var getMovieDetails: GetMovieDetails
    private lateinit var playMovie: PlayMovie

    @Mock private lateinit var moviesRepository: MoviesRepository
    @Mock private lateinit var navigator: Navigator

    @Before
    fun setUp() {
        playMovie = PlayMovie(context(), navigator)
        getMovieDetails = GetMovieDetails(moviesRepository)
        movieDetailsViewModel = MovieDetailsViewModel(getMovieDetails, playMovie)
    }

    @Test fun `loading movie details should update live data`() {
        val movieDetails = MovieDetails(0, "IronMan", "poster", "summary",
                "cast", "director", 2018, "trailer")
        given { moviesRepository.movieDetails(0) }.willReturn(Right(movieDetails))

        movieDetailsViewModel.loadMovieDetails(0)

        val movie = movieDetailsViewModel.movieDetails.value

        with(movie!!) {
            id shouldEqualTo 0
            title shouldEqualTo "IronMan"
            poster shouldEqualTo "poster"
            summary shouldEqualTo "summary"
            cast shouldEqualTo "cast"
            director shouldEqualTo "director"
            year shouldEqualTo 2018
            trailer shouldEqualTo "trailer"
        }
    }
}