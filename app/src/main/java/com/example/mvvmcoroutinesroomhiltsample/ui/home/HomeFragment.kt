package com.example.mvvmcoroutinesroomhiltsample.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmcoroutinesroomhiltsample.data.model.ApiUser
import com.example.mvvmcoroutinesroomhiltsample.databinding.FragmentHomeBinding
import com.example.mvvmcoroutinesroomhiltsample.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var _binding: FragmentHomeBinding
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding

    private lateinit var adapter: ApiUserAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupUI()
        setupObserver()
        return root
    }

    private fun setupUI() {
        _binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter =
            ApiUserAdapter(
                arrayListOf()
            )
        _binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                _binding.recyclerView.context,
                (_binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        _binding.recyclerView.adapter = adapter
    }

    /*private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                MainRepository(
                    ApiHelperImpl(RetrofitBuilder.apiService),
                    DatabaseHelperImpl(DatabaseBuilder.getInstance(activity?.applicationContext!!))
                )
            )
        ).get(HomeViewModel::class.java)
    }*/

    private fun setupObserver() {
        activity?.let { it ->
            homeViewModel.getUsers().observe(it, {
                when (it.status) {
                    Status.SUCCESS -> {
                        _binding.progressBar.visibility = View.GONE
                        it.data?.let { users -> renderList(users) }
                        _binding.recyclerView.visibility = View.VISIBLE
                    }
                    Status.LOADING -> {
                        _binding.progressBar.visibility = View.VISIBLE
                        _binding.recyclerView.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        //Handle Error
                        _binding.progressBar.visibility = View.GONE
                        Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
    }

    private fun renderList(users: List<ApiUser>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}