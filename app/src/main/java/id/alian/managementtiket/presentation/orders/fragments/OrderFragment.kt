package id.alian.managementtiket.presentation.orders.fragments

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.alian.managementtiket.R
import id.alian.managementtiket.commons.*
import id.alian.managementtiket.databinding.FragmentOrderBinding
import id.alian.managementtiket.presentation.BaseFragment
import id.alian.managementtiket.presentation.orders.activities.OrderDetailActivity
import id.alian.managementtiket.presentation.orders.adapter.OrderAdapter
import id.alian.managementtiket.presentation.orders.viewmodel.OrderViewModel
import id.alian.managementtiket.presentation.payment.PaymentActivity
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class OrderFragment :
    BaseFragment<FragmentOrderBinding>(FragmentOrderBinding::inflate) {

    private val orderAdapter by lazy { OrderAdapter(requireContext()) }
    private val viewModel: OrderViewModel by viewModels()

    override fun FragmentOrderBinding.initialize() {
        requireActivity().runOnUiThread {
            setupRecyclerView()

            topAppBar.setNavigationOnClickListener {
                requireActivity().finish()
            }

            orderAdapter.setOnItemClickListener { order ->
                requireContext().openActivity(PaymentActivity::class.java, extras = {
                    putSerializable("order", order)
                })
            }

            orderAdapter.detailOrder { order ->
                requireContext().openActivity(OrderDetailActivity::class.java, extras = {
                    putSerializable("order", order)
                })
            }

            swipeUpRefresh.setOnRefreshListener {
                viewModel.getOrders()
            }

            lifecycleScope.launchWhenStarted {
                viewModel.orderListState.collect {
                    requireActivity().runOnUiThread {
                        when (it) {
                            is Resource.Success -> {
                                swipeUpRefresh.isRefreshing = false
                                linearProgressIndicator.remove()
                                orderAdapter.differ.submitList(it.data)
                            }

                            is Resource.Error -> {
                                swipeUpRefresh.isRefreshing = false
                                linearProgressIndicator.remove()
                                root.showShortSnackBar(
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
        with(binding) {
            rvOrderList.adapter = orderAdapter
            rvOrderList.layoutManager = LinearLayoutManager(requireContext())
            rvOrderList.addItemDecoration(
                DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
            )
        }
    }
}