package id.alian.managementtiket.presentation.orders.fragments

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.alian.managementtiket.R
import id.alian.managementtiket.commons.*
import id.alian.managementtiket.databinding.FragmentOrderDashboardBinding
import id.alian.managementtiket.presentation.orders.adapter.OrderAdapter
import id.alian.managementtiket.presentation.orders.viewmodel.OrderViewModel
import id.alian.managementtiket.presentation.users.state.UserListState
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class OrderDashboardFragment :
    BaseFragment<FragmentOrderDashboardBinding>(FragmentOrderDashboardBinding::inflate) {

    private val orderAdapter by lazy { OrderAdapter() }
    private val viewModel: OrderViewModel by viewModels()

    override fun FragmentOrderDashboardBinding.initialize() {
        requireActivity().runOnUiThread {
            setupRecyclerView()
        }
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launchWhenStarted {
            viewModel.orderListState.collect {
                requireActivity().runOnUiThread {
                    when (it) {
                        is Resource.Success -> {
                            hideLoading()
                            orderAdapter.differ.submitList(it.data)
                        }

                        is Resource.Loading -> {
                            showLoading()
                        }

                        is Resource.Error -> {
                            hideLoading()
                            binding.root.showShortSnackBar(
                                message = it.message!!,
                                colorHex = requireContext().getColorCompat(R.color.error_red)
                            )
                        }
                        else -> UserListState.Empty
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvOrderList.adapter = orderAdapter
        binding.rvOrderList.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun hideLoading() {
        binding.progressBar.hide()
        binding.rvOrderList.show()
    }

    private fun showLoading() {
        binding.progressBar.show()
        binding.rvOrderList.hide()
    }
}