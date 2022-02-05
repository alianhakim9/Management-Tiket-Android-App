package id.alian.managementtiket.presentation.auth.fragments

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import id.alian.managementtiket.R
import id.alian.managementtiket.commons.*
import id.alian.managementtiket.databinding.FragmentRegisterBinding
import id.alian.managementtiket.domain.model.User
import id.alian.managementtiket.presentation.BaseFragment
import id.alian.managementtiket.presentation.MainActivity
import id.alian.managementtiket.presentation.auth.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val viewModel: AuthViewModel by viewModels()

    override fun FragmentRegisterBinding.initialize() {
        binding.btnRegister.setOnClickListener {
            viewModel.register(
                User(
                    email = binding.etEmail.editText?.text.toString(),
                    name = binding.etName.editText?.text.toString(),
                    password = binding.etPassword.editText?.text.toString()
                )
            )
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.registerState.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        showLoading()
                    }

                    is Resource.Success -> {
                        requireContext().openActivity(MainActivity::class.java)
                        requireActivity().finish()
                    }

                    is Resource.Error -> {
                        hideLoading()
                        binding.root.showShortSnackBarWithAction(
                            message = it.message!!,
                            actionLabel = resources.getString(R.string.ok),
                            block = { snackBar ->
                                snackBar.dismiss()
                            },
                            colorHex = requireContext().getColorCompat(R.color.error_red),
                            actionLabelColor = requireContext().getColorCompat(R.color.white)
                        )
                    }
                }
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.show()
        binding.etEmail.disable()
        binding.etPassword.disable()
    }

    private fun hideLoading() {
        binding.progressBar.hide()
        binding.etEmail.enable()
        binding.etPassword.enable()
    }

}