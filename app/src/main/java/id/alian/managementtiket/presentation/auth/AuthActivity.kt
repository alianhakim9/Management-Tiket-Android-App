package id.alian.managementtiket.presentation.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import id.alian.managementtiket.R
import id.alian.managementtiket.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        runOnUiThread {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.authFragmentContainerView) as NavHostFragment
            navHostFragment.navController
        }
    }
}