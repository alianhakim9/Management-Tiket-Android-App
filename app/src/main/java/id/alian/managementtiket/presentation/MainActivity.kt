package id.alian.managementtiket.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.alian.managementtiket.databinding.ActivityMainBinding
import id.alian.managementtiket.presentation.orders.OrderActivity
import id.alian.managementtiket.presentation.tickets.TicketActivity
import id.alian.managementtiket.presentation.users.UserActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        runOnUiThread {
            setContentView(binding.root)
            binding.cvUser.setOnClickListener {
                Intent(this, UserActivity::class.java).also {
                    startActivity(it)
                }
            }

            binding.cvTicket.setOnClickListener {
                Intent(this, TicketActivity::class.java).also {
                    startActivity(it)
                }
            }

            binding.cvOrder.setOnClickListener {
                Intent(this, OrderActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }
}