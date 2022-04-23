package com.arifwidayana.challengechapter5.view.homepage

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.arifwidayana.challengechapter5.R
import com.arifwidayana.challengechapter5.adapter.NowPlayingMoviesAdapter
import com.arifwidayana.challengechapter5.adapter.PopularMoviesAdapter
import com.arifwidayana.challengechapter5.adapter.TopRatedAdapter
import com.arifwidayana.challengechapter5.adapter.UpComingAdapter
import com.arifwidayana.challengechapter5.databinding.FragmentMainHomepageBinding
import com.arifwidayana.challengechapter5.model.DatabaseStore
import com.arifwidayana.challengechapter5.model.pojo.nowplaying.ResultNowPlaying
import com.arifwidayana.challengechapter5.model.pojo.popular.ResultPopular
import com.arifwidayana.challengechapter5.model.pojo.toprated.ResultTopRated
import com.arifwidayana.challengechapter5.model.pojo.upcoming.ResultUpComing
import com.arifwidayana.challengechapter5.model.utils.Constant
import com.arifwidayana.challengechapter5.model.utils.SharedHelper
import com.arifwidayana.challengechapter5.viewmodel.MovieViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainHomepageFragment : Fragment() {
    private var bind : FragmentMainHomepageBinding? = null
    private val binding get() = bind!!
    private var user: DatabaseStore? = null
    private val movieViewModel: MovieViewModel by viewModels()
    private lateinit var shared: SharedHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentMainHomepageBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shared = SharedHelper(requireContext())
        user = DatabaseStore.getData(requireContext())
        binding.apply {
            CoroutineScope(Dispatchers.Main).launch {
                val getName = user?.userDao()?.getUsername(shared.getString(Constant.USERNAME).toString())
                tvShowName.text = "Hi, ${getName?.name}"
            }
            ivProfile.setOnClickListener {
                findNavController().navigate(R.id.action_mainHomepageFragment_to_profileUserFragment)
            }

            movieViewModel.apply {
                loading.observe(viewLifecycleOwner){
                    when {
                        it -> {
                            pbMovies.visibility = View.VISIBLE
                        }
                        else -> {
                            pbMovies.visibility = View.GONE
                            clDisplay.visibility = View.VISIBLE
                        }
                    }
                }

                dataNowPlayingMovies.observe(viewLifecycleOwner){
                    fetchNowPlayingMovies(it.results)
                }

                dataPopularMovies.observe(viewLifecycleOwner){
                    fetchPopularMovies(it.results)
                }

                dataUpComingMovies.observe(viewLifecycleOwner){
                    fetchUpComingMovies(it.results)
                }

                dataTopRatedMovies.observe(viewLifecycleOwner){
                    fetchTopRated(it.results)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bind = null
    }

    private fun fetchNowPlayingMovies(results: List<ResultNowPlaying>) {
        val adapter = NowPlayingMoviesAdapter{
            binding.apply {
                val parcel = MainHomepageFragmentDirections.actionMainHomepageFragmentToDetailMoviesFragment()
                parcel.id = it.id
                findNavController().navigate(parcel)
            }
        }
        adapter.submitList(results)
        binding.rvMovieNowPlaying.adapter = adapter
    }

    private fun fetchPopularMovies(results: List<ResultPopular>) {
        val adapter = PopularMoviesAdapter{
            binding.apply {
                val parcel = MainHomepageFragmentDirections.actionMainHomepageFragmentToDetailMoviesFragment()
                parcel.id = it.id
                findNavController().navigate(parcel)
            }
        }
        adapter.submitList(results)
        binding.rvMoviePopular.adapter = adapter
    }

    private fun fetchUpComingMovies(results: List<ResultUpComing>) {
        val adapter = UpComingAdapter{
            binding.apply {
                val parcel = MainHomepageFragmentDirections.actionMainHomepageFragmentToDetailMoviesFragment()
                parcel.id = it.id
                findNavController().navigate(parcel)
            }
        }
        adapter.submitList(results)
        binding.rvMovieUpComing.adapter = adapter
    }

    private fun fetchTopRated(results: List<ResultTopRated>) {
        val adapter = TopRatedAdapter{
            binding.apply {
                val parcel = MainHomepageFragmentDirections.actionMainHomepageFragmentToDetailMoviesFragment()
                parcel.id = it.id
                findNavController().navigate(parcel)
            }
        }
        adapter.submitList(results)
        binding.rvMovieTopRated.adapter = adapter
    }
}