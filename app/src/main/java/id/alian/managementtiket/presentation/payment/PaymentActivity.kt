package id.alian.managementtiket.presentation.payment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import id.alian.managementtiket.R
import id.alian.managementtiket.commons.*
import id.alian.managementtiket.databinding.ActivityPaymentBinding
import id.alian.managementtiket.domain.model.Order
import id.alian.managementtiket.presentation.orders.activities.OrderDetailActivity
import id.alian.managementtiket.presentation.payment.viewmodel.PaymentViewModel
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding
    private val viewModel: PaymentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val banks = listOf("BCA", "Mandiri", "Muamalat", "Mandiri Syari'ah")
        val adapter = ArrayAdapter(this, R.layout.dropdown_list_item, banks)
        val autoCompleteTextView = (binding.bankMenu.editText as? AutoCompleteTextView)

        with(binding) {
            val order = intent.getSerializableExtra("order") as? Order
            setupOrderTicketView(order!!)

            bankMenu.remove()
            etUserBankCode.remove()
            etUserBankCode.editText?.addTextChangedListener(paymentTextWatcher)

            rbPaymentMethod.setOnCheckedChangeListener { _, i ->
                when (i) {
                    R.id.bank -> {
                        bankMenu.show()
                        etUserBankCode.show()
                        autoCompleteTextView?.setAdapter(adapter)
                    }
                }
            }

            btnPay.setOnClickListener {
                viewModel.addPayment(
                    orderId = order.id,
                    bankId = autoCompleteTextView?.text.toString(),
                    userBankCode = etUserBankCode.editText?.text.toString()
                )
            }

            lifecycleScope.launchWhenStarted {
                viewModel.addPaymentState.collectLatest {
                    when (it) {
                        is Resource.Loading -> {
                            btnPay.disable()
                            btnPay.text = resources.getString(R.string.btn_is_payment)
                        }

                        is Resource.Success -> {
                            btnPay.enable()
                            btnPay.text = resources.getString(R.string.btn_pay)
                            root.showShortSnackBarWithAction(
                                message = "Pembayaran berhasil",
                                actionLabel = "Detail Order",
                                block = {
                                    openActivity(OrderDetailActivity::class.java, extras = {
                                        putSerializable("order", order)
                                    })
                                    finish()
                                },
                                colorHex = getColorCompat(R.color.success),
                                actionLabelColor = getColorCompat(R.color.white)
                            )
                        }

                        is Resource.Error -> {
                            btnPay.enable()
                            btnPay.text = resources.getString(R.string.btn_pay)
                            root.showShortSnackBar(
                                message = resources.getString(R.string.err_message),
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

    @SuppressLint("SetTextI18n")
    private fun setupOrderTicketView(order: Order) {
        with(binding) {
            tvTicketTo.text = "To : ${order.ticket.to}"
            tvTicketFrom.text = "From : ${order.ticket.from}"
            tvTicketCount.text = "Count : ${order.ticket_count}"
            tvSumPrice.text = "Rp. ${order.price}"
            tvTicketTime.text = "Time : ${order.ticket.time}"
        }
    }

    private val paymentTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val userBankCode = binding.etUserBankCode.editText?.text.toString().trim()
            binding.btnPay.isEnabled = userBankCode.isNotEmpty()
        }

        override fun afterTextChanged(s: Editable) {}
    }

}