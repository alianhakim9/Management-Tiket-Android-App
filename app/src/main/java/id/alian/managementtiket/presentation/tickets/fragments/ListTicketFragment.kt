package id.alian.managementtiket.presentation.tickets.fragments

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.alian.managementtiket.databinding.FragmentListTicketBinding
import id.alian.managementtiket.presentation.BaseFragment
import id.alian.managementtiket.presentation.tickets.adapter.TicketPaginateAdapter
import id.alian.managementtiket.presentation.tickets.state.TicketLoadStateAdapter
import id.alian.managementtiket.presentation.tickets.viewmodel.TicketViewModel
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ListTicketFragment :
    BaseFragment<FragmentListTicketBinding>(FragmentListTicketBinding::inflate) {

    private val viewModel: TicketViewModel by viewModels()
    private val adapter: TicketPaginateAdapter by lazy { TicketPaginateAdapter() }

    override fun FragmentListTicketBinding.initialize() {
        setupRecyclerView()
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.tickets.collect {
                adapter.submitData(it)
            }
        }

        adapter.setOnItemClickListener {
            findNavController().navigate(
                ListTicketFragmentDirections.actionListTicketPaginationFragmentToDetailTicketFragment(
                    it
                )
            )
        }

        topAppBar.setNavigationOnClickListener {
            requireActivity().finish()
        }
    }

    private fun setupRecyclerView() {
        with(binding) {
            rvTicketList.adapter = adapter.withLoadStateFooter(
                footer = TicketLoadStateAdapter {
                    adapter.retry()
                }
            )
            rvTicketList.layoutManager = LinearLayoutManager(requireContext())
        }
    }
}