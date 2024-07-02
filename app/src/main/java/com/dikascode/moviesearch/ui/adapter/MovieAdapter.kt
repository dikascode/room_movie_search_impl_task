package com.dikascode.moviesearch.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dikascode.moviesearch.R
import com.dikascode.moviesearch.data.model.Movie

class MovieAdapter(private val onItemClick: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var movies: List<Movie> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener { onItemClick(movie) }
    }

    override fun getItemCount() = movies.size

    fun setMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            itemView.findViewById<TextView>(R.id.titleTextView).text = movie.title
            itemView.findViewById<TextView>(R.id.yearTextView).text = movie.year

            val posterImageView = itemView.findViewById<ImageView>(R.id.moviePosterImageView)
            Glide.with(itemView.context)
                .load(movie.poster)
                .into(posterImageView)

        }
    }
}