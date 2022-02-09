package id.alian.managementtiket.presentation.tickets.fragments

import android.annotation.SuppressLint
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.alian.managementtiket.commons.Resource
import id.alian.managementtiket.commons.remove
import id.alian.managementtiket.commons.show
import id.alian.managementtiket.commons.showShortToast
import id.alian.managementtiket.databinding.FragmentSearchTicketBinding
import id.alian.managementtiket.presentation.BaseFragment
import id.alian.managementtiket.presentation.tickets.adapter.TicketAdapter
import id.alian.managementtiket.presentation.tickets.viewmodel.TicketViewModel
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SearchTicketFragment :
    BaseFragment<FragmentSearchTicketBinding>(FragmentSearchTicketBinding::inflate) {

    private val viewModel: TicketViewModel by viewModels()
    private val adapter by lazy { TicketAdapter() }

    override fun FragmentSearchTicketBinding.initialize() {
        setupRecyclerView()
        with(viewModel) {
            with(binding) {
                btnSearch.setOnClickListener {
                    searchTicket(
                        from = etFromFilter.text.toString().trim().lowercase(),
                        to = etToFilter.text.toString().trim().lowercase()
                    )
                }
            }

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                searchTicketState.collectLatest {
                    when (it) {
                        is Resource.Loading -> {
                            showLoading()
                        }

                        is Resource.Success -> {
                            hideLoading()
                            adapter.differ.submitList(it.data)
                        }

                        is Resource.Error -> {
                            showError(it.message!!)
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        with(binding) {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showLoading() {
        with(binding) {
            tv.text = "Loading..."
            tv.show()
            recyclerView.remove()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun hideLoading() {
        with(binding) {
            tv.remove()
            recyclerView.show()
        }
    }

    private fun showError(error: String) {
        with(binding) {
            tv.text = error
            tv.show()
            recyclerView.remove()
        }
    }
}