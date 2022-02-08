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
                        binding.tvFrom.text = "Dari : ${it.data?.from}"
                        binding.tvTo.text = "Tujuan : ${it.data?.to}"
                        binding.tvBankId.text = "Kode Bank : ${it.data?.bank_id}"
                        binding.tvCodeFixed.text = "Kode Tiket : ${it.data?.code_fixed}"
                        binding.tvName.text = it.data?.name
                        binding.tvSumPrice.text = "Harga : Rp. ${it.data?.sum_price}"
                        binding.tvTime.text = "Waktu : ${it.data?.time}"
                        binding.tvTicketCount.text =
                            "Jumlah Tiket : ${it.data?.ticket_count}"
                        binding.tvTicketCode.text = it.data?.code_fixed
                    }

                    is Resource.Error -> {
                        hideLoading()
                        binding.root.showShortSnackBar(
                            message = it.message!!,
                            colorHex = getColorCompat(R.color.error_red)
                        )
                    }
                }
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
    }


    private fun showLoading() {
        binding.linearProgressIndicator.show()
        binding.cardView.remove()
    }

    private fun hideLoading() {
        binding.linearProgressIndicator.remove()
        binding.cardView.show()
        binding.imgBarcode.show()
        binding.tvTicketCode.show()
    }
}