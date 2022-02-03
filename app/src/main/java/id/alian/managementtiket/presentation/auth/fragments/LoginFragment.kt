package id.alian.managementtiket.presentation.auth.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import id.alian.managementtiket.R
import id.alian.managementtiket.databinding.FragmentLoginBinding
import id.alian.managementtiket.presentation.MainActivity

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        requireActivity().runOnUiThread {
            binding.btnLogin.setOnClickListener {
                Intent(requireContext(), MainActivity::class.java).also {
                    startActivity(it)
                }
            }

            binding.btnToRegister.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}