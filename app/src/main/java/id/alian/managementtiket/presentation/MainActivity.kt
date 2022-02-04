package id.alian.managementtiket.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import id.alian.managementtiket.commons.openActivity
import id.alian.managementtiket.commons.showMaterialAlertDialog
import id.alian.managementtiket.databinding.ActivityMainBinding
import id.alian.managementtiket.presentation.auth.AuthActivity
import id.alian.managementtiket.presentation.datastore.DataStoreViewModel
import id.alian.managementtiket.presentation.orders.OrderActivity
import id.alian.managementtiket.presentation.tickets.TicketActivity
import id.alian.managementtiket.presentation.users.UserActivity

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: DataStoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        runOnUiThread {
            viewModel.getToken()
            setContentView(binding.root)
            binding.cvUser.setOnClickListener {
                this.openActivity(UserActivity::class.java)
            }

            binding.cvTicket.setOnClickListener {
                this.openActivity(TicketActivity::class.java)
            }

            binding.cvOrder.setOnClickListener {
                this.openActivity(OrderActivity::class.java)
            }

            binding.cvLogout.setOnClickListener {
                this.showMaterialAlertDialog(
                    positiveButtonLabel = "Keluar",
                    actionOnPositiveButton = {
                        viewModel.logout()
                        Intent(this, AuthActivity::class.java).also {
                            startActivity(it)
                            finish()
                        }
                    },
                    negativeButtonLabel = "Batal",
                    title = "Keluar",
                    message = "Anda yakin ?"
                )
            }
        }
    }
}