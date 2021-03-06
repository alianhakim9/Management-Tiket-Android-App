package id.alian.managementtiket.presentation.tickets

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import dagger.hilt.android.AndroidEntryPoint
import id.alian.managementtiket.R
import id.alian.managementtiket.databinding.ActivityTicketBinding

@AndroidEntryPoint
class TicketActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTicketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigation()
    }

    private fun setupNavigation() {
        with(binding) {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.ticketFragmentContainer) as NavHostFragment
            NavigationUI.setupWithNavController(bottomNavigation, navHostFragment.navController)
        }
    }
}