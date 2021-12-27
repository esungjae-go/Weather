package com.esungjae.weather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.esungjae.weather.databinding.WeatherFragmentBinding
import com.esungjae.weather.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private val viewModel: WeatherViewModel by viewModels()

    private lateinit var adapter: WeatherAdapter

    private lateinit var binding: WeatherFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WeatherFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initRefresh()
        observeLiveData()
    }

    private fun initRecyclerView() {
        adapter = WeatherAdapter()
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@WeatherFragment.adapter
        }
    }

    private fun initRefresh() {
        with(binding.swipeRefreshLayout) {
            setOnRefreshListener {
                adapter.swapItems(null)
                viewModel.setLocationQuery("se")
                isRefreshing = false
            }
        }
    }

    private fun observeLiveData() {
        with(viewModel) {
            loading.observe(viewLifecycleOwner) { isLoading ->
                binding.progressBar.visibility = if (isLoading) VISIBLE else GONE
            }
            localWeathers.observe(viewLifecycleOwner) { weathers ->
                adapter.swapItems(weathers)
            }
            if (getLocationQuery().isNullOrEmpty()) setLocationQuery("se")
        }
    }
}