package id.alian.managementtiket.domain.use_case.tickets.get_tickets_pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.alian.managementtiket.data.repository.TicketRepository
import id.alian.managementtiket.domain.model.Ticket
import javax.inject.Inject

class TicketPagingSource @Inject constructor(
    private val repository: TicketRepository
) : PagingSource<Int, Ticket>() {
    override fun getRefreshKey(state: PagingState<Int, Ticket>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Ticket> {
        return try {
            val nextPageNumber = params.key ?: 0
            val response = repository.getTickets(page = nextPageNumber)
            return LoadResult.Page(
                data = response.data,
                prevKey = null,
                nextKey = if (nextPageNumber < response.last_page) nextPageNumber.plus(1) else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}