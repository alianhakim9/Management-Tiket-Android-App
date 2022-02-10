package id.alian.managementtiket.presentation.auth.fragments

import android.graphics.Color.WHITE
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.alian.managementtiket.R
import id.alian.managementtiket.commons.*
import id.alian.managementtiket.commons.Constants.ERROR_NO_INTERNET_CONNECTION
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

            val spannable = SpannableString("Halaman Login")
            spannable.setSpan(
                ForegroundColorSpan(requireContext().getColorCompat(R.color.success)),
                8, 13,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            titleLogin.text = spannable

            with(loginTextWatcher) {
                etEmail.editText?.addTextChangedListener(this)
                etPassword.editText?.addTextChangedListener(this)
            }

            btnToRegister.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }

            btnLogin.setOnClickListener {
                viewModel.login(
                    email = binding.etEmail.editText?.text.toString(),
                    password = binding.etPassword.editText?.text.toString()
                )
            }

            with(viewLifecycleOwner) {
                lifecycleScope.launchWhenCreated {
                    with(viewModel) {
                        login.collectLatest {
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
                                    root.showShortSnackBarWithAction(
                                        message = it.message!!,
                                        actionLabel = resources.getString(R.string.snackBar_ok),
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
            }
        }
    }

    private fun showLoading() {
        with(binding) {
            btnLogin.text = resources.getString(R.string.btn_is_login)
            etEmail.disable()
            etPassword.disable()
            btnLogin.disable()
            btnToRegister.disable()
        }
    }

    private fun hideLoading() {
        with(binding) {
            btnLogin.text = resources.getString(R.string.btn_login)
            etEmail.enable()
            etPassword.enable()
            btnLogin.enable()
            btnToRegister.enable()
        }
    }

    private val loginTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            with(binding) {
                val email = etEmail.editText?.text.toString().trim()
                val password = etPassword.editText?.text.toString().trim()
                btnLogin.isEnabled = email.isNotEmpty() && password.isNotEmpty()
            }
        }

        override fun afterTextChanged(p0: Editable?) {
        }
    }
}