package id.alian.managementtiket.presentation.users

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import id.alian.managementtiket.R
import id.alian.managementtiket.databinding.ActivityUserBinding

@AndroidEntryPoint
class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        runOnUiThread {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.userFragmentContainer) as NavHostFragment
            navHostFragment.navController
        }

    }
}