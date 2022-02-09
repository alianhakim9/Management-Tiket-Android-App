package id.alian.managementtiket.presentation.orders.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import id.alian.managementtiket.R
import id.alian.managementtiket.commons.*
import id.alian.managementtiket.databinding.ActivityOrderDetailBinding
import id.alian.managementtiket.domain.model.Order
import id.alian.managementtiket.presentation.orders.viewmodel.OrderViewModel
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class OrderDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderDetailBinding
    private val viewModel: OrderViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            val order = intent.getSerializableExtra("order") as? Order
            order?.id?.let {
                viewModel.orderDetail(
                    orderId = it
                )
            }

            lifecycleScope.launchWhenStarted {
                viewModel.orderDetailState.collectLatest {
                    when (it) {
                        is Resource.Loading -> {
                            showLoading()
                        }

                        is Resource.Success -> {
                            hideLoading()
                            tvFrom.text = "Dari : ${it.data?.from}"
                            tvTo.text = "Tujuan : ${it.data?.to}"
                            tvBankId.text = "Kode Bank : ${it.data?.bank_id}"
                            tvCodeFixed.text = "Kode Tiket : ${it.data?.code_fixed}"
                            tvName.text = it.data?.name
                            tvSumPrice.text = "Harga : Rp. ${it.data?.sum_price}"
                            tvTime.text = "Waktu : ${it.data?.time}"
                            tvTicketCount.text =
                                "Jumlah Tiket : ${it.data?.ticket_count}"
                            tvTicketCode.text = it.data?.code_fixed
                        }

                        is Resource.Error -> {
                            hideLoading()
                            root.showShortSnackBar(
                                message = it.message!!,
                                colorHex = getColorCompat(R.color.error_red)
                            )
                        }
                    }
                }
            }

            topAppBar.setNavigationOnClickListener {
                finish()
            }
        }
    }


    private fun showLoading() {
        with(binding) {
            linearProgressIndicator.show()
            cardView.remove()
        }
    }

    private fun hideLoading() {
        with(binding) {
            linearProgressIndicator.remove()
            cardView.show()
            imgBarcode.show()
            tvTicketCode.show()
        }
    }
}