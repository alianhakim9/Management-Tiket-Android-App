package id.alian.managementtiket.presentation.tickets.fragments

import android.annotation.SuppressLint
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import id.alian.managementtiket.R
import id.alian.managementtiket.commons.*
import id.alian.managementtiket.databinding.FragmentDetailTicketBinding
import id.alian.managementtiket.presentation.BaseFragment
import id.alian.managementtiket.presentation.orders.viewmodel.OrderViewModel
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class DetailTicketFragment :
    BaseFragment<FragmentDetailTicketBinding>(FragmentDetailTicketBinding::inflate) {

    private val args: DetailTicketFragmentArgs by navArgs()
    private val viewModel: OrderViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun FragmentDetailTicketBinding.initialize() {
        requireActivity().runOnUiThread {
            binding.tvTicketTo.text = "To : ${args.ticketDetail?.to}"
            binding.tvTicketFrom.text = "From : ${args.ticketDetail?.from}"
            binding.tvTicketTime.text = "Time : ${args.ticketDetail?.time}"
            binding.tvTicketPrice.text = "Price : $ ${args.ticketDetail?.price}"
            binding.tvTicketStock.text = "Stock : ${args.ticketDetail?.ticket_stock}"

            viewModel.ticketCount.observe(viewLifecycleOwner) {
                binding.tvTicketCountUser.text = it.toString()
            }

            binding.btnDecrease.setOnClickListener {
                viewModel.decreaseCount()
            }

            binding.btnIncrease.setOnClickListener {
                viewModel.increaseCount()
            }

            binding.btnBuy.setOnClickListener {
                viewModel.createOrder(
                    ticketId = args.ticketDetail?.id!!,
                    ticketCount = binding.tvTicketCountUser.text.toString().toInt(),
                    price = args.ticketDetail?.price.toString().toInt()
                )
            }

            binding.topAppBar.setNavigationOnClickListener {
                findNavController().navigate(
                    DetailTicketFragmentDirections.actionDetailTicketFragmentToTicketListFragment()
                )
            }

            lifecycleScope.launchWhenCreated {
                viewModel.createOrderState.collectLatest {
                    when (it) {
                        is Resource.Loading -> {
                            binding.btnBuy.text = resources.getString(R.string.is_order_button)
                            binding.btnBuy.disable()
                        }

                        is Resource.Success -> {
                            binding.btnBuy.text = resources.getString(R.string.text_order_button)
                            binding.btnBuy.enable()
                            binding.root.showShortSnackBarWithAction(
                                message = "Berhasil ditambahkan ke keranjang",
                                actionLabel = "Ok",
                                block = { snackBar ->
                                    snackBar.dismiss()
                                },
                                colorHex = requireContext().getColorCompat(R.color.success),
                                actionLabelColor = requireContext().getColorCompat(R.color.black)
                            )
                        }

                        is Resource.Error -> {
                            binding.btnBuy.text = resources.getString(R.string.text_order_button)
                            binding.btnBuy.enable()
                            binding.root.showShortSnackBar(
                                message = it.message!!,
                                colorHex = requireContext().getColorCompat(R.color.error_red)
                            )
                        }
                    }
                }
            }
        }

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    // Handle the back button event
                    findNavController().navigate(
                        DetailTicketFragmentDirections.actionDetailTicketFragmentToTicketListFragment()
                    )
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }


}