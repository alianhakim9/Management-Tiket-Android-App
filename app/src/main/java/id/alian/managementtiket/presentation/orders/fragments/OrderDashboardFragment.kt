package id.alian.managementtiket.presentation.orders.fragments

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.alian.managementtiket.R
import id.alian.managementtiket.commons.*
import id.alian.managementtiket.databinding.FragmentOrderDashboardBinding
import id.alian.managementtiket.presentation.BaseFragment
import id.alian.managementtiket.presentation.orders.adapter.OrderAdapter
import id.alian.managementtiket.presentation.orders.viewmodel.OrderViewModel
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class OrderDashboardFragment :
    BaseFragment<FragmentOrderDashboardBinding>(FragmentOrderDashboardBinding::inflate) {

    private val orderAdapter by lazy { OrderAdapter() }
    private val viewModel: OrderViewModel by viewModels()

    override fun FragmentOrderDashboardBinding.initialize() {
        requireActivity().runOnUiThread {
            setupRecyclerView()
            binding.topAppBar.setNavigationOnClickListener {
                requireActivity().finish()
            }

            lifecycleScope.launchWhenStarted {
                viewModel.orderListState.collect {
                    requireActivity().runOnUiThread {
                        when (it) {
                            is Resource.Success -> {
                                binding.linearProgressIndicator.remove()
                                orderAdapter.differ.submitList(it.data)
                            }

                            is Resource.Error -> {
                                binding.linearProgressIndicator.remove()
                                binding.root.showShortSnackBar(
                                    message = it.message!!,
                                    colorHex = requireContext().getColorCompat(R.color.error_red)
                                )
                            }
                            else -> Unit
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvOrderList.adapter = orderAdapter
        binding.rvOrderList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvOrderList.addItemDecoration(
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        )
    }
}