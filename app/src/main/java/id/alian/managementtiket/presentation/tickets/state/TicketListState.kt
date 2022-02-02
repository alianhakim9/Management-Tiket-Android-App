package id.alian.managementtiket.presentation.tickets.state

import id.alian.managementtiket.domain.model.Ticket

sealed class TicketListState {
    data class Success(val data: List<Ticket>) : TicketListState()
    data class Error(val message: String) : TicketListState()
    object Loading : TicketListState()
    object Empty : TicketListState()
}