package id.alian.managementtiket.presentation

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import id.alian.managementtiket.R
import id.alian.managementtiket.commons.Resource
import id.alian.managementtiket.commons.openActivity
import id.alian.managementtiket.commons.showMaterialAlertDialog
import id.alian.managementtiket.databinding.ActivityMainBinding
import id.alian.managementtiket.presentation.auth.AuthActivity
import id.alian.managementtiket.presentation.datastore.DataStoreViewModel
import id.alian.managementtiket.presentation.orders.OrderActivity
import id.alian.managementtiket.presentation.tickets.TicketActivity
import id.alian.managementtiket.presentation.users.UserActivity
import id.alian.managementtiket.presentation.users.viewmodel.UserViewModel
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: DataStoreViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        runOnUiThread {
            viewModel.getToken()
            setContentView(binding.root)
            userViewModel.profile()

            binding.cvTicket.setOnClickListener {
                this.openActivity(TicketActivity::class.java)
            }

            binding.topAppBar.setOnMenuItemClickListener {
                when (it.itemId) {
//                    R.id.profile -> {
//                        this.openActivity(UserActivity::class.java)
//                        true
//                    }

                    R.id.logout -> {
                        this.showMaterialAlertDialog(
                            positiveButtonLabel = "Keluar",
                            actionOnPositiveButton = {
                                viewModel.logout()
                                this.openActivity(AuthActivity::class.java)
                                finish()
                            },
                            negativeButtonLabel = "Batal",
                            title = "Keluar",
                            message = "Anda yakin ?"
                        )
                        true
                    }

                    R.id.order -> {
                        this.openActivity(OrderActivity::class.java)
                        true
                    }
                    else -> false
                }
            }

            binding.topAppBar.setNavigationOnClickListener {
                binding.drawerLayout.open()
            }

            val header = binding.navigationView.getHeaderView(0)
            val name = header.findViewById<TextView>(R.id.header_title)
            val email = header.findViewById<TextView>(R.id.header_text)

            lifecycleScope.launchWhenStarted {
                userViewModel.userState.collectLatest {
                    when (it) {
                        is Resource.Success -> {
                            name.text = it.data?.name
                            email.text = it.data?.email
                        }
                    }
                }
            }
        }
    }
}