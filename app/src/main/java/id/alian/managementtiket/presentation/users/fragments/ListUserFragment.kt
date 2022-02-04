package id.alian.managementtiket.presentation.users.fragments

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.alian.managementtiket.commons.BaseFragment
import id.alian.managementtiket.commons.Resource
import id.alian.managementtiket.commons.hide
import id.alian.managementtiket.commons.show
import id.alian.managementtiket.databinding.FragmentListUserBinding
import id.alian.managementtiket.presentation.users.adapter.UserAdapter
import id.alian.managementtiket.presentation.users.viewmodel.UserViewModel
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ListUserFragment : BaseFragment<FragmentListUserBinding>(FragmentListUserBinding::inflate) {

    private val userAdapter by lazy { UserAdapter() }
    private val viewModel: UserViewModel by viewModels()

    override fun FragmentListUserBinding.initialize() {
        requireActivity().runOnUiThread {
            setupRecyclerView()

        }
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launchWhenStarted {
            viewModel.userListState.collect {
                when (it) {
                    is Resource.Success -> {
                        hideLoading()
                        userAdapter.differ.submitList(it.data)
                    }

                    is Resource.Loading -> {
                        showLoading()
                    }

                    is Resource.Error -> {
                        hideLoading()
                        Snackbar.make(binding.root, it.message!!, Snackbar.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
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
        binding.progressBar.hide()
        binding.rvUserList.show()
    }

    private fun showLoading() {
        binding.progressBar.show()
        binding.rvUserList.hide()
    }

}