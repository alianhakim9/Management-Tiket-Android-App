package id.alian.managementtiket.presentation.payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.alian.managementtiket.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}