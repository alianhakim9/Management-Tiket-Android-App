package id.alian.managementtiket.presentation.tickets.fragments

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.alian.managementtiket.R
import id.alian.managementtiket.commons.*
import id.alian.managementtiket.databinding.FragmentListTicketBinding
import id.alian.managementtiket.presentation.tickets.adapter.TicketAdapter
import id.alian.managementtiket.presentation.tickets.viewmodel.TicketViewModel
import id.alian.managementtiket.presentation.users.state.UserListState
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class ListTicketFragment :
    BaseFragment<FragmentListTicketBinding>(FragmentListTicketBinding::inflate) {

    private val ticketAdapter by lazy { TicketAdapter() }
    private val viewModel: TicketViewModel by viewModels()

    override fun FragmentListTicketBinding.initialize() {
        requireActivity().runOnUiThread {
            setupRecyclerView()
            ticketAdapter.setOnItemClickListener {
                findNavController().navigate(
                    ListTicketFragmentDirections.actionTicketListFragmentToDetailTicketFragment(
                        it
                    )
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launchWhenStarted {
            viewModel.ticketListState.collect {
                requireActivity().runOnUiThread {
                    when (it) {
                        is Resource.Success -> {
                            hideLoading()
                            ticketAdapter.differ.submitList(it.data)
                        }

                        is Resource.Loading -> {
                            showLoading()
                        }

                        is Resource.Error -> {
                            hideLoading()
                            binding.root.showShortSnackBar(
                                message = it.message!!,
                                colorHex = requireContext().getColorCompat(R.color.error_red)
                            )
                        }
                        else -> Unit
                    }
                }
            }
        }
    }


    private fun setupRecyclerView() {
        binding.rvTicketList.adapter = ticketAdapter
        binding.rvTicketList.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun hideLoading() {
        binding.progressBar.hide()
        binding.rvTicketList.show()
    }

    private fun showLoading() {
        binding.progressBar.show()
        binding.rvTicketList.hide()
    }
}