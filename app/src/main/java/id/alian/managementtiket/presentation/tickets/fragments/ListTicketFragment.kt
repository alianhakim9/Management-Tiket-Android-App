package id.alian.managementtiket.presentation.tickets.fragments

import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.alian.managementtiket.R
import id.alian.managementtiket.commons.Resource
import id.alian.managementtiket.commons.getColorCompat
import id.alian.managementtiket.commons.remove
import id.alian.managementtiket.commons.showShortSnackBar
import id.alian.managementtiket.databinding.FragmentListTicketBinding
import id.alian.managementtiket.presentation.BaseFragment
import id.alian.managementtiket.presentation.tickets.adapter.TicketAdapter
import id.alian.managementtiket.presentation.tickets.viewmodel.TicketViewModel
import kotlinx.coroutines.flow.collectLatest

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
                requireActivity().finish()
            }

            lifecycleScope.launchWhenStarted {
                viewModel.ticketListState.collectLatest {
                    requireActivity().runOnUiThread {
                        when (it) {
                            is Resource.Success -> {
                                binding.swipeUpRefresh.isRefreshing = false
                                binding.linearProgressIndicator.remove()
                                ticketAdapter.differ.submitList(it.data)
                            }

                            is Resource.Error -> {
                                binding.swipeUpRefresh.isRefreshing = false
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

            binding.etFromFilter.addTextChangedListener(ticketTextWatcher)
            binding.etToFilter.addTextChangedListener(ticketTextWatcher)

            binding.swipeUpRefresh.setOnRefreshListener {
                viewModel.getTickets()
                binding.swipeUpRefresh.isRefreshing = true
            }

            binding.btnFilter.setOnClickListener {
                viewModel.getFilteredTickets(
                    from = binding.etFromFilter.text.toString().trim().lowercase(),
                    to = binding.etToFilter.text.toString().trim().lowercase(),
                )
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvTicketList.adapter = ticketAdapter
        binding.rvTicketList.layoutManager = LinearLayoutManager(requireContext())
    }

    private val ticketTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val from = binding.etFromFilter.text.toString().trim()
            val to = binding.etToFilter.text.toString().trim()
            if (from.isEmpty() && to.isEmpty()) {
                viewModel.getTickets()
            }
        }

        override fun afterTextChanged(p0: Editable?) {
        }
    }
}