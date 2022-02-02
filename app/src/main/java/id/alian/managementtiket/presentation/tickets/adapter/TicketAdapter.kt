package id.alian.managementtiket.presentation.tickets.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.alian.managementtiket.databinding.TicketItemLayoutBinding
import id.alian.managementtiket.domain.model.Ticket

class TicketAdapter : RecyclerView.Adapter<TicketAdapter.ViewHolder>() {
    class ViewHolder(val binding: TicketItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Ticket>() {
        override fun areItemsTheSame(oldItem: Ticket, newItem: Ticket): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: Ticket, newItem: Ticket): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TicketItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tickets = differ.currentList[position]

        holder.itemView.apply {
            setOnClickListener {
                onItemClickListener?.let {
                    it(tickets)
                }
            }
        }

        holder.binding.tvTicketFrom.text = "From : ${tickets.from}"
        holder.binding.tvTicketTo.text = "To : ${tickets.to}"
        holder.binding.tvTicketTime.text = "Time : ${tickets.time}"
        holder.binding.tvTicketPrice.text = "Price :$ ${tickets.price}"
        holder.binding.tvTicketStock.text = "Stok : ${tickets.ticket_stock}"
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Ticket) -> Unit)? = null

    fun setOnItemClickListener(listener: (Ticket) -> Unit) {
        onItemClickListener = listener
    }
}