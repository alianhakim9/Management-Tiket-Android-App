package id.alian.managementtiket.presentation.tickets.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.alian.managementtiket.databinding.ItemTicketLayoutBinding
import id.alian.managementtiket.domain.model.Ticket

class TicketPaginateAdapter :
    PagingDataAdapter<Ticket, TicketPaginateAdapter.ViewHolder>(TicketComparator) {
    override fun onBindViewHolder(holder: TicketPaginateAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bindTicket(it)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TicketPaginateAdapter.ViewHolder {
        return ViewHolder(
            ItemTicketLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    object TicketComparator : DiffUtil.ItemCallback<Ticket>() {
        override fun areItemsTheSame(oldItem: Ticket, newItem: Ticket): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Ticket, newItem: Ticket): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, TicketComparator)

    var ticketFilterList = mutableListOf<Ticket>()

    init {
        ticketFilterList = differ.currentList
    }

    inner class ViewHolder(val binding: ItemTicketLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindTicket(item: Ticket) = with(binding) {
            tvTicketFrom.text = item.from
            tvTicketTo.text = item.to
            tvTicketPrice.text = item.price
            tvTicketStock.text = item.ticket_stock
            itemView.apply {
                setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }
    }

    private var onItemClickListener: ((Ticket) -> Unit)? = null

    fun setOnItemClickListener(listener: (Ticket) -> Unit) {
        onItemClickListener = listener
    }
}
