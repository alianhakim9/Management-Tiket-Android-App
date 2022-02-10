package id.alian.managementtiket.presentation.users.fragments

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import id.alian.managementtiket.presentation.BaseFragment
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
                        tvUserName.text = "Loading..."
                    }

                    is Resource.Success -> {
                        tvUserName.text = "Hello ${it.data?.name}"
                    }
                    is Resource.Error -> {
                        tvUserName.text = "Error!"
                    }
                }
            }
        }
    }
}