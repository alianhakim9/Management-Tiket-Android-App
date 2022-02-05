package id.alian.managementtiket.presentation.auth.fragments

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.alian.managementtiket.R
import id.alian.managementtiket.commons.*
import id.alian.managementtiket.databinding.FragmentLoginBinding
import id.alian.managementtiket.presentation.BaseFragment
import id.alian.managementtiket.presentation.MainActivity
import id.alian.managementtiket.presentation.auth.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: AuthViewModel by viewModels()

    override fun FragmentLoginBinding.initialize() {
        requireActivity().runOnUiThread {
            binding.btnToRegister.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }

            binding.btnLogin.setOnClickListener {
                viewModel.login(
                    email = binding.etEmail.editText?.text.toString(),
                    password = binding.etPassword.editText?.text.toString()
                )
            }

            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.login.collectLatest {
                    when (it) {
                        is Resource.Loading -> {
                            showLoading()
                        }

                        is Resource.Success -> {
                            requireActivity().openActivity(MainActivity::class.java)
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
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun showLoading() {
        binding.btnLogin.text = resources.getString(R.string.is_login_button)
        binding.etEmail.disable()
        binding.etPassword.disable()
        binding.btnLogin.disable()
        binding.btnToRegister.disable()
    }

    private fun hideLoading() {
        binding.btnLogin.text = resources.getString(R.string.text_login_button)
        binding.etEmail.enable()
        binding.etPassword.enable()
        binding.btnLogin.enable()
        binding.btnToRegister.enable()
    }
}