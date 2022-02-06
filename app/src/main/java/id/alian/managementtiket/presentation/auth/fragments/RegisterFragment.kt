package id.alian.managementtiket.presentation.auth.fragments

import android.text.Editable
import android.text.TextWatcher
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

        binding.etName.editText?.addTextChangedListener(registerTextWatcher)
        binding.etEmail.editText?.addTextChangedListener(registerTextWatcher)
        binding.etPassword.editText?.addTextChangedListener(registerTextWatcher)

        binding.btnRegister.setOnClickListener {
            viewModel.register(
                User(
                    email = binding.etEmail.editText?.text.toString(),
                    name = binding.etName.editText?.text.toString(),
                    password = binding.etPassword.editText?.text.toString()
                )
            )
        }

        binding.btnToLogin.setOnClickListener {
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
        binding.btnRegister.text = resources.getString(R.string.is_register_button)
        binding.etName.disable()
        binding.etEmail.disable()
        binding.etPassword.disable()
    }

    private fun hideLoading() {
        binding.btnRegister.text = resources.getString(R.string.text_register_button)
        binding.etName.enable()
        binding.etEmail.enable()
        binding.etPassword.enable()
    }

    private val registerTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val name = binding.etName.editText?.text.toString().trim()
            val email = binding.etEmail.editText?.text.toString().trim()
            val password = binding.etPassword.editText?.text.toString().trim()
            binding.btnRegister.isEnabled =
                name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()
        }

        override fun afterTextChanged(p0: Editable?) {

        }
    }

}