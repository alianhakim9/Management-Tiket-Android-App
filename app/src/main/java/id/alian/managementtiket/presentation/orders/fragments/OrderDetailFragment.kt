package id.alian.managementtiket.presentation.orders.fragments

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.alian.managementtiket.R
import id.alian.managementtiket.commons.*
import id.alian.managementtiket.databinding.FragmentOrderDetailBinding
import id.alian.managementtiket.presentation.BaseFragment
import id.alian.managementtiket.presentation.orders.viewmodel.OrderViewModel
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class OrderDetailFragment :
    BaseFragment<FragmentOrderDetailBinding>(FragmentOrderDetailBinding::inflate) {

    private val viewModel: OrderViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun FragmentOrderDetailBinding.initialize() {

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.orderDetailState.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        showLoading()
                    }

                    is Resource.Success -> {
                        hideLoading()
                        binding.tvFrom.text = "From : ${it.data?.get(0)?.from}"
                        binding.tvTo.text = "To : ${it.data?.get(0)?.to}"
                        binding.tvSumPrice.text = "Rp : ${it.data?.get(0)?.sum_price}"
                        binding.tvBankId.text = "Bank : ${it.data?.get(0)?.bank_id}"
                        binding.tvTicketCount.text =
                            "Jumlah tiket : ${it.data?.get(0)?.ticket_count}"
                        binding.tvTime.text = "Time : ${it.data?.get(0)?.time}"
                        binding.tvName.text = it.data?.get(0)?.name
                        binding.tvCodeFixed.text = it.data?.get(0)?.code_fixed
                    }

                    is Resource.Error -> {
                        hideLoading()
                        binding.root.showShortSnackBar(
                            message = it.message!!,
                            colorHex = requireContext().getColorCompat(R.color.error_red)
                        )
                    }
                }
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            findNavController().navigate(OrderDetailFragmentDirections.actionOrderDetailFragmentToOrderDashboardFragment())
        }
    }

    private fun showLoading() {
        binding.linearProgressIndicator.show()
    }

    private fun hideLoading() {
        binding.linearProgressIndicator.remove()
        binding.cardView.show()
        binding.cardView.show()
        binding.tvFrom.show()
        binding.tvTo.show()
        binding.tvSumPrice.show()
        binding.tvBankId.show()
        binding.tvTicketCount.show()
        binding.tvName.show()
        binding.tvCodeFixed.show()
        binding.tvTime.show()
    }

}