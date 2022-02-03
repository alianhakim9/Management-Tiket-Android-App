package id.alian.managementtiket.presentation.users.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.alian.managementtiket.databinding.FragmentListUserBinding
import id.alian.managementtiket.presentation.users.adapter.UserAdapter
import id.alian.managementtiket.presentation.users.state.UserListState
import id.alian.managementtiket.presentation.users.viewmodel.UserViewModel
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ListUserFragment : Fragment() {

    private var _binding: FragmentListUserBinding? = null
    private val binding get() = _binding!!
    private val userAdapter by lazy { UserAdapter() }
    private val viewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListUserBinding.inflate(inflater, container, false)
        requireActivity().runOnUiThread {
            setupRecyclerView()
            lifecycleScope.launchWhenStarted {
                viewModel.userListState.collect {
                    when (it) {
                        is UserListState.Success -> {
                            hideLoading()
                            userAdapter.differ.submitList(it.data)
                        }

                        is UserListState.Loading -> {
                            showLoading()
                        }

                        is UserListState.Error -> {
                            hideLoading()
                            Snackbar.make(binding.root, it.message, Snackbar.LENGTH_SHORT).show()
                        }
                        else -> UserListState.Empty
                    }
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        binding.rvUserList.adapter = userAdapter
        binding.rvUserList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvUserList.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.rvUserList.visibility = View.VISIBLE
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.rvUserList.visibility = View.GONE
    }

}