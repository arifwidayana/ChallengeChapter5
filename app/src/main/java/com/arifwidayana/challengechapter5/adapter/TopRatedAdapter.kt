package com.arifwidayana.challengechapter5.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arifwidayana.challengechapter5.databinding.ItemCardMoviesBinding
import com.arifwidayana.challengechapter5.model.pojo.toprated.ResultTopRated
import com.arifwidayana.challengechapter5.model.utils.Constant
import com.bumptech.glide.Glide

class TopRatedAdapter(private val onClick : (ResultTopRated) -> Unit) : ListAdapter<ResultTopRated, TopRatedAdapter.MoviesHolder>(
    Differ()
) {
    class MoviesHolder(private val binding : ItemCardMoviesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun result(currentMovies: ResultTopRated, onClick: (ResultTopRated) -> Unit) {
            binding.apply {
                tvTitle.text = currentMovies.title
                tvOverview.text = currentMovies.overview
                tvRate.text = currentMovies.voteAverage.toString()
                Glide.with(root)
                    .load("${ Constant.BASE_IMAGE}${currentMovies.posterPath}")
                    .into(ivPoster)
                root.setOnClickListener {
                    onClick(currentMovies)
                }
            }
        }
    }

    class Differ : DiffUtil.ItemCallback<ResultTopRated>() {
        override fun areItemsTheSame(oldItem: ResultTopRated, newItem: ResultTopRated): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ResultTopRated, newItem: ResultTopRated): Boolean {
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