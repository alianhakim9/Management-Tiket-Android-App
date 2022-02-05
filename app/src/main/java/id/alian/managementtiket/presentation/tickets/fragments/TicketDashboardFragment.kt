package id.alian.managementtiket.presentation.tickets.fragments

import androidx.navigation.fragment.findNavController
import id.alian.managementtiket.R
import id.alian.managementtiket.presentation.BaseFragment
import id.alian.managementtiket.databinding.FragmentTicketDashboardBinding

class TicketDashboardFragment :
    BaseFragment<FragmentTicketDashboardBinding>(FragmentTicketDashboardBinding::inflate) {

    override fun FragmentTicketDashboardBinding.initialize() {
        binding.cvListTicket.setOnClickListener {
            findNavController().navigate(R.id.action_ticketDashboardFragment_to_ticketListFragment)
        }
    }

}