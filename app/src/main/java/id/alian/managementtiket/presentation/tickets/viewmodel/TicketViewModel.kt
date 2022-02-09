package id.alian.managementtiket.presentation.tickets.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.alian.managementtiket.commons.Resource
import id.alian.managementtiket.data.repository.TicketRepository
import id.alian.managementtiket.domain.model.Ticket
import id.alian.managementtiket.domain.use_case.tickets.get_tickets_pagination.TicketPagingSource
import id.alian.managementtiket.domain.use_case.tickets.search_ticket.SearchTicketUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val repository: TicketRepository,
    private val searchTicketUseCase: SearchTicketUseCase
) : ViewModel() {

    private val _searchTicketState = MutableSharedFlow<Resource<List<Ticket>>>()
    val searchTicketState: SharedFlow<Resource<List<Ticket>>> = _searchTicketState

    val tickets = Pager(
        config = PagingConfig(
            pageSize = 1
        ),
        pagingSourceFactory = {
            TicketPagingSource(repository)
        }
    ).flow.cachedIn(viewModelScope)

    fun searchTicket(from: String, to: String) {
        if (from.isNotEmpty() || to.isNotEmpty()) {
            searchTicketUseCase(
                from = from,
                to = to
            ).onEach { result ->
                _searchTicketState.emit(result)
            }.launchIn(viewModelScope)
        }
    }
}