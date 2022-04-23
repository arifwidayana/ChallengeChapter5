package com.arifwidayana.challengechapter5.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arifwidayana.challengechapter5.databinding.ItemCardMoviesBinding
import com.arifwidayana.challengechapter5.model.pojo.popular.ResultPopular
import com.bumptech.glide.Glide

class PopularMoviesAdapter(private val onClick: (ResultPopular) -> Unit) : ListAdapter<ResultPopular, PopularMoviesAdapter.MoviesHolder>(
    Differ()
) {

    class MoviesHolder(private val binding : ItemCardMoviesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun result(currentMovies: ResultPopular, onClick: (ResultPopular) -> Unit){
            binding.apply {
                tvTitle.text = currentMovies.title
                tvOverview.text = currentMovies.overview
                tvRate.text = currentMovies.voteAverage.toString()
                Glide.with(root)
                    .load("https://image.tmdb.org/t/p/w500${currentMovies.posterPath}")
                    .into(ivPoster)
                root.setOnClickListener{
                    onClick(currentMovies)
                }
            }

        }
    }

    class Differ : DiffUtil.ItemCallback<ResultPopular>() {
        override fun areItemsTheSame(oldItem: ResultPopular, newItem: ResultPopular): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ResultPopular, newItem: ResultPopular): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
        val binding = ItemCardMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
        holder.result(getItem(position), onClick)
    }
}