package id.alian.managementtiket.presentation.tickets.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.alian.managementtiket.databinding.ItemTicketLayoutBinding
import id.alian.managementtiket.domain.model.Ticket

class TicketAdapter : RecyclerView.Adapter<TicketAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemTicketLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Ticket>() {
        override fun areItemsTheSame(oldItem: Ticket, newItem: Ticket): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: Ticket, newItem: Ticket): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    var countryFilterList = mutableListOf<Ticket>()

    init {
        countryFilterList = differ.currentList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTicketLayoutBinding.inflate(
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

        with(holder.binding) {
            tvTicketFrom.text = "Dari : ${tickets.from}"
            tvTicketTo.text = "Tujuan : ${tickets.to}"
            tvTicketTime.text = "Waktu Keberangkatan : ${tickets.time}"
            tvTicketPrice.text = "Rp. ${tickets.price}"
            tvTicketStock.text = "Stok : ${tickets.ticket_stock}"
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Ticket) -> Unit)? = null

    fun setOnItemClickListener(listener: (Ticket) -> Unit) {
        onItemClickListener = listener
    }
}