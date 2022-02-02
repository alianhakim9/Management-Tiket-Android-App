package id.alian.managementtiket.presentation.tickets.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.alian.managementtiket.commons.Resource
import id.alian.managementtiket.domain.use_case.tickets.get_tickets.GetTicketsUseCase
import id.alian.managementtiket.presentation.tickets.state.TicketListState
import id.alian.managementtiket.presentation.users.state.UserListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    val getTicketsUseCase: GetTicketsUseCase
) : ViewModel() {

    private val _ticketListState = MutableStateFlow<TicketListState>(TicketListState.Empty)
    val ticketListState: StateFlow<TicketListState> = _ticketListState

    init {
        getTickets()
    }


    private fun getTickets() {
        getTicketsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    if (result.data != null) {
                        _ticketListState.value = TicketListState.Success(result.data)
                    }
                }

                is Resource.Error -> {
                    _ticketListState.value =
                        TicketListState.Error(result.message ?: "an expected error occurred")
                }

                is Resource.Loading -> {
                    _ticketListState.value = TicketListState.Loading
                }
            }
        }.launchIn(viewModelScope)
    }
}