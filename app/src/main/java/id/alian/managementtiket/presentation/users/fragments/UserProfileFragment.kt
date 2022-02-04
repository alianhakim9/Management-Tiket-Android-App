package id.alian.managementtiket.presentation.users.fragments

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import id.alian.managementtiket.commons.BaseFragment
import id.alian.managementtiket.commons.Resource
import id.alian.managementtiket.databinding.FragmentUserProfileBinding
import id.alian.managementtiket.presentation.users.viewmodel.UserViewModel
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class UserProfileFragment :
    BaseFragment<FragmentUserProfileBinding>(FragmentUserProfileBinding::inflate) {

    private val viewModel: UserViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun FragmentUserProfileBinding.initialize() {
        viewModel.profile()
        lifecycleScope.launchWhenStarted {
            viewModel.userState.collect {
                when (it) {
                    is Resource.Loading -> {
                        binding.tvUserName.text = "Loading..."
                    }

                    is Resource.Success -> {
                        binding.tvUserName.text = "Hello ${it.data?.name}"
                    }
                    is Resource.Error -> {
                        binding.tvUserName.text = "Error!"
                    }
                    else -> Unit
                }
            }
        }
    }
}