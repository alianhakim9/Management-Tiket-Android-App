package id.alian.managementtiket.presentation.tickets.fragments

import android.annotation.SuppressLint
import android.util.Log
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
import id.alian.managementtiket.presentation.orders.OrderActivity
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
            with(binding) {
                tvTicketTo.text = "Dari : ${args.ticketDetail?.to}"
                tvTicketFrom.text = "Tujuan : ${args.ticketDetail?.from}"
                tvTicketTime.text = "Waktu keberangkatan : ${args.ticketDetail?.time}"
                tvTicketPrice.text = "Harga : Rp. ${args.ticketDetail?.price}"
                tvTicketStock.text = "Stok tiket : ${args.ticketDetail?.ticket_stock}"

                with(viewModel) {
                    ticketCount.observe(viewLifecycleOwner) {
                        tvTicketCountUser.text = it.toString()
                    }

                    btnDecrease.setOnClickListener {
                        decreaseCount()
                    }

                    btnIncrease.setOnClickListener {
                        increaseCount()
                    }

                    btnBuy.setOnClickListener {
                        createOrder(
                            ticketId = args.ticketDetail?.id!!,
                            ticketCount = tvTicketCountUser.text.toString().toInt(),
                            price = args.ticketDetail?.price.toString().toInt()
                        )
                    }

                    topAppBar.setNavigationOnClickListener {
                        findNavController().navigate(
                            DetailTicketFragmentDirections.actionDetailTicketFragmentToListTicketPaginationFragment()
                        )
                    }

                    lifecycleScope.launchWhenCreated {
                        createOrderPaymentState.collectLatest {
                            when (it) {
                                is Resource.Loading -> {
                                    btnBuy.text = resources.getString(R.string.is_order_button)
                                    btnBuy.disable()
                                }

                                is Resource.Success -> {
                                    btnBuy.text =
                                        resources.getString(R.string.text_order_button)
                                    btnBuy.enable()
                                    root.showShortSnackBarWithAction(
                                        message = "Berhasil ditambahkan ke order",
                                        actionLabel = resources.getString(R.string.ok),
                                        block = {
                                            requireActivity().openActivity(OrderActivity::class.java)
                                        },
                                        colorHex = requireContext().getColorCompat(R.color.success),
                                        actionLabelColor = requireContext().getColorCompat(R.color.white)
                                    )
                                }

                                is Resource.Error -> {
                                    btnBuy.text =
                                        resources.getString(R.string.text_order_button)
                                    btnBuy.enable()
                                    root.showShortSnackBar(
                                        message = it.message!!,
                                        colorHex = requireContext().getColorCompat(R.color.error_red)
                                    )
                                }
                            }
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
                        DetailTicketFragmentDirections.actionDetailTicketFragmentToListTicketPaginationFragment()
                    )
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }


}