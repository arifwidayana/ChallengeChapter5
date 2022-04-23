package com.arifwidayana.challengechapter5.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arifwidayana.challengechapter5.databinding.ItemCardMoviesBinding
import com.arifwidayana.challengechapter5.model.pojo.upcoming.ResultUpComing
import com.arifwidayana.challengechapter5.model.utils.Constant
import com.bumptech.glide.Glide

class UpComingAdapter(private val onClick: (ResultUpComing) -> Unit) : ListAdapter<ResultUpComing, UpComingAdapter.MoviesHolder>(
    Differ()
) {
    class MoviesHolder(private val binding : ItemCardMoviesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun result(currentMovies : ResultUpComing, onClick: (ResultUpComing) -> Unit){
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

    class Differ : DiffUtil.ItemCallback<ResultUpComing>() {
        override fun areItemsTheSame(oldItem: ResultUpComing, newItem: ResultUpComing): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ResultUpComing, newItem: ResultUpComing): Boolean {
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