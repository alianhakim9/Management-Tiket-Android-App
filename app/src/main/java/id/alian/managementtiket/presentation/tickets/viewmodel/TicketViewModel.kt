package id.alian.managementtiket.presentation.tickets.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.alian.managementtiket.data.repository.TicketRepository
import id.alian.managementtiket.domain.use_case.tickets.get_tickets_pagination.TicketPagingSource
import javax.inject.Inject


@HiltViewModel
class TicketViewModel @Inject constructor(
    private val repository: TicketRepository
) : ViewModel() {

    val tickets = Pager(
        config = PagingConfig(
            pageSize = 5
        ),
        pagingSourceFactory = {
            TicketPagingSource(repository)
        }
    ).flow.cachedIn(viewModelScope)
}