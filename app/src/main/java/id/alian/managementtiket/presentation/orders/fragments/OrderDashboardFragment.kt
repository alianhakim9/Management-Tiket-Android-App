package id.alian.managementtiket.presentation.orders.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.alian.managementtiket.databinding.FragmentOrderDashboardBinding
import id.alian.managementtiket.presentation.orders.adapter.OrderAdapter
import id.alian.managementtiket.presentation.orders.state.OrderListState
import id.alian.managementtiket.presentation.orders.viewmodel.OrderViewModel
import id.alian.managementtiket.presentation.users.state.UserListState
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class OrderDashboardFragment : Fragment() {

    private val orderAdapter by lazy { OrderAdapter() }
    private val viewModel: OrderViewModel by viewModels()

    private var _binding: FragmentOrderDashboardBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderDashboardBinding.inflate(inflater, container, false)

        requireActivity().runOnUiThread {
            setupRecyclerView()

        }

        lifecycleScope.launchWhenStarted {
            viewModel.orderListState.collect {
                requireActivity().runOnUiThread {
                    when (it) {
                        is OrderListState.Success -> {
                            hideLoading()
                            orderAdapter.differ.submitList(it.data)
                        }

                        is OrderListState.Loading -> {
                            showLoading()
                        }

                        is OrderListState.Error -> {
                            hideLoading()
                            Snackbar.make(binding.root, it.message, Snackbar.LENGTH_SHORT).show()
                        }
                        else -> UserListState.Empty
                    }
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        binding.rvOrderList.adapter = orderAdapter
        binding.rvOrderList.layoutManager = LinearLayoutManager(requireContext())
    }


    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.rvOrderList.visibility = View.VISIBLE
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.rvOrderList.visibility = View.GONE
    }

}