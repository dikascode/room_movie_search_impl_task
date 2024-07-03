package com.dikascode.moviesearch.ui

import android.os.Bundle
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.dikascode.moviesearch.R
import com.dikascode.moviesearch.data.model.MovieDetailResponse
import com.dikascode.moviesearch.databinding.FragmentMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        bindMovieDetails(args.movie)
        return binding.root
    }

    private fun bindMovieDetails(movieDetail: MovieDetailResponse) {
        binding.apply {
            titleTextView.text = movieDetail.title
            yearTextView.text = getHtmlText(R.string.movie_detail_year, movieDetail.year)
            genreTextView.text = getHtmlText(R.string.movie_detail_genre, movieDetail.genre)
            directorTextView.text = getHtmlText(R.string.movie_detail_director, movieDetail.director)
            actorsTextView.text = getHtmlText(R.string.movie_detail_actors, movieDetail.actors)
            plotTextView.text = getHtmlText(R.string.movie_detail_plot, movieDetail.plot)
            ratingTextView.text = getHtmlText(R.string.movie_detail_imdb_rating, movieDetail.imdbRating)

            Glide.with(requireContext())
                .load(movieDetail.poster)
                .into(posterImageView)
        }
    }

    private fun getHtmlText(stringResId: Int, value: String): Spanned {
        val text = getString(stringResId, value)
        return HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
