package id.alian.managementtiket.presentation.auth

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import id.alian.managementtiket.R
import id.alian.managementtiket.commons.openActivity
import id.alian.managementtiket.databinding.ActivityAuthBinding
import id.alian.managementtiket.presentation.MainActivity
import id.alian.managementtiket.presentation.datastore.DataStoreViewModel

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private val viewModel: DataStoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getToken()
        if (viewModel.token.value != null) {
            this.openActivity(MainActivity::class.java)
            finish()
        }

        runOnUiThread {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.authFragmentContainerView) as NavHostFragment
            navHostFragment.navController
        }
    }
}