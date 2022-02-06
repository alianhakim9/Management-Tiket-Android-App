package id.alian.managementtiket.presentation.tickets.fragments

import android.util.Log
import androidx.compose.ui.text.toLowerCase
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.alian.managementtiket.R
import id.alian.managementtiket.commons.*
import id.alian.managementtiket.databinding.FragmentListTicketBinding
import id.alian.managementtiket.domain.model.Ticket
import id.alian.managementtiket.presentation.BaseFragment
import id.alian.managementtiket.presentation.tickets.adapter.TicketAdapter
import id.alian.managementtiket.presentation.tickets.viewmodel.TicketViewModel
import kotlinx.coroutines.flow.collectLatest
import java.util.*
import kotlin.collections.ArrayList

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
            binding.topAppBar.setNavigationOnClickListener {
                findNavController().navigate(
                    ListTicketFragmentDirections.actionTicketListFragmentToTicketDashboardFragment()
                )
            }

            lifecycleScope.launchWhenStarted {
                viewModel.ticketListState.collectLatest {
                    requireActivity().runOnUiThread {
                        when (it) {
                            is Resource.Success -> {
                                binding.linearProgressIndicator.remove()
                                ticketAdapter.differ.submitList(it.data)
                            }

                            is Resource.Error -> {
                                binding.linearProgressIndicator.remove()
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

            binding.btnFilter.setOnClickListener {
                ticketAdapter.filter.filter(
                    binding.etFromFilter.text.toString()
                )
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvTicketList.adapter = ticketAdapter
        binding.rvTicketList.layoutManager = LinearLayoutManager(requireContext())
    }
}