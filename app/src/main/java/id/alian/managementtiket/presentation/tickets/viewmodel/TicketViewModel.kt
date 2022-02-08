package id.alian.managementtiket.presentation.tickets.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.alian.managementtiket.commons.Resource
import id.alian.managementtiket.domain.model.Ticket
import id.alian.managementtiket.domain.use_case.tickets.get_filtered_tickets.GetFilteredTicketsUseCase
import id.alian.managementtiket.domain.use_case.tickets.get_tickets.GetTicketsUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    val getTicketsUseCase: GetTicketsUseCase,
    val getFilteredTicketsUseCase: GetFilteredTicketsUseCase
) : ViewModel() {

    private val _ticketListState = MutableSharedFlow<Resource<List<Ticket>>>()
    val ticketListState: SharedFlow<Resource<List<Ticket>>> = _ticketListState

    init {
        getTickets()
    }

    fun getTickets() {
        getTicketsUseCase().onEach { result ->
            _ticketListState.emit(result)
        }.launchIn(viewModelScope)
    }

    fun getFilteredTickets(from: String, to: String) {
        if (from.isNotEmpty() || to.isNotEmpty()) {
            getFilteredTicketsUseCase(
                from = from,
                to = to
            ).onEach { result ->
                _ticketListState.emit(result)
            }.launchIn(viewModelScope)
        }
    }
}