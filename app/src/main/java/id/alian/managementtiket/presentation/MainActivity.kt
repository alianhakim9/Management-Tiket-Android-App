package id.alian.managementtiket.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.alian.managementtiket.R
import id.alian.managementtiket.databinding.ActivityMainBinding
import id.alian.managementtiket.presentation.tickets.activities.TicketActivity
import id.alian.managementtiket.presentation.users.activities.UserActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
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
    }
}