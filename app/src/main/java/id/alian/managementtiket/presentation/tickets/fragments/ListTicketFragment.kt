package id.alian.managementtiket.presentation.tickets.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.alian.managementtiket.databinding.FragmentListTicketBinding
import id.alian.managementtiket.presentation.tickets.adapter.TicketAdapter
import id.alian.managementtiket.presentation.tickets.state.TicketListState
import id.alian.managementtiket.presentation.tickets.viewmodel.TicketViewModel
import id.alian.managementtiket.presentation.users.state.UserListState
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class ListTicketFragment : Fragment() {

    private var _binding: FragmentListTicketBinding? = null
    private val binding get() = _binding!!
    private val ticketAdapter by lazy { TicketAdapter() }
    private val viewModel: TicketViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListTicketBinding.inflate(inflater, container, false)
        setupRecyclerView()

        lifecycleScope.launchWhenStarted {
            viewModel.ticketListState.collect {
                requireActivity().runOnUiThread {
                    when (it) {
                        is TicketListState.Success -> {
                            hideLoading()
                            ticketAdapter.differ.submitList(it.data)
                        }

                        is TicketListState.Loading -> {
                            showLoading()
                        }

                        is TicketListState.Error -> {
                            hideLoading()
                            Snackbar.make(binding.root, it.message, Snackbar.LENGTH_SHORT).show()
                        }
                        else -> UserListState.Empty
                    }
                }
            }
        }

        ticketAdapter.setOnItemClickListener {
            findNavController().navigate(
                ListTicketFragmentDirections.actionTicketListFragmentToDetailTicketFragment(
                    it
                )
            )
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        binding.rvTicketList.adapter = ticketAdapter
        binding.rvTicketList.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.rvTicketList.visibility = View.VISIBLE
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.rvTicketList.visibility = View.GONE
    }
}