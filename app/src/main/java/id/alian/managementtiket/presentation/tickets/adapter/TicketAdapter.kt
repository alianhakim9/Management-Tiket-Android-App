package id.alian.managementtiket.presentation.tickets.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.alian.managementtiket.databinding.ItemTicketLayoutBinding
import id.alian.managementtiket.domain.model.Ticket

class TicketAdapter : RecyclerView.Adapter<TicketAdapter.ViewHolder>(), Filterable {
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

        holder.binding.tvTicketFrom.text = "Dari : ${tickets.from}"
        holder.binding.tvTicketTo.text = "Tujuan : ${tickets.to}"
        holder.binding.tvTicketTime.text = "Waktu Keberangkatan : ${tickets.time}"
        holder.binding.tvTicketPrice.text = "Harga Tiket : Rp. ${tickets.price}"
        holder.binding.tvTicketStock.text = "Stok Tiket : ${tickets.ticket_stock}"
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Ticket) -> Unit)? = null

    fun setOnItemClickListener(listener: (Ticket) -> Unit) {
        onItemClickListener = listener
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    Log.d("TicketAdapter", "performFiltering: Empty")
                } else {
                    val resultList = ArrayList<Ticket>()
                    for (row in differ.currentList) {
                        Log.d("TicketAdapter", "performFiltering: $row")
                    }
                    countryFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = countryFilterList
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(p0: CharSequence?, result: FilterResults?) {
                countryFilterList = result?.values as ArrayList<Ticket>
                notifyDataSetChanged()
            }
        }
    }
}