package id.alian.managementtiket.presentation.auth.fragments

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
        val spannable = SpannableString("Halaman Register")
        spannable.setSpan(
            ForegroundColorSpan(requireContext().getColorCompat(R.color.success)),
            0, 7,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        titleRegister.text = spannable

        etName.editText?.addTextChangedListener(registerTextWatcher)
        etEmail.editText?.addTextChangedListener(registerTextWatcher)
        etPassword.editText?.addTextChangedListener(registerTextWatcher)

        btnRegister.setOnClickListener {
            viewModel.register(
                User(
                    email = etEmail.editText?.text.toString(),
                    name = etName.editText?.text.toString(),
                    password = etPassword.editText?.text.toString()
                )
            )
        }

        btnToLogin.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
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

    private fun showLoading() {
        with(binding) {
            btnRegister.text = resources.getString(R.string.btn_is_register)
            btnRegister.disable()
            btnToLogin.disable()
            etName.disable()
            etEmail.disable()
            etPassword.disable()
        }
    }

    private fun hideLoading() {
        with(binding) {
            btnRegister.text = resources.getString(R.string.btn_register)
            btnRegister.enable()
            btnToLogin.enable()
            etName.enable()
            etEmail.enable()
            etPassword.enable()
        }
    }

    private val registerTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            with(binding) {
                val name = etName.editText?.text.toString().trim()
                val email = etEmail.editText?.text.toString().trim()
                val password = etPassword.editText?.text.toString().trim()

                btnRegister.isEnabled =
                    name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && password.length > 8
            }
        }

        override fun afterTextChanged(p0: Editable?) {

        }
    }

}