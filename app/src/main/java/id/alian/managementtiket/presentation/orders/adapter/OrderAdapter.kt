package id.alian.managementtiket.presentation.orders.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.alian.managementtiket.databinding.OrderItemLayoutBinding
import id.alian.managementtiket.domain.model.Order

class OrderAdapter : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {
    class ViewHolder(val binding: OrderItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            OrderItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orders = differ.currentList[position]
        holder.binding.tvTicketFrom.text = "From : ${orders.ticket.from}"
        holder.binding.tvTicketTo.text = "To : ${orders.ticket.to}"
        holder.binding.tvTicketTime.text = "Time : ${orders.ticket.time}"
        holder.binding.tvTicketPrice.text = "Price :$ ${orders.price}"
        if (orders.status != "0") {
            holder.binding.tvTicketStatus.text = "Sudah Dibayar"
            holder.binding.tvTicketStatus.setTextColor(Color.GREEN)
        } else {
            holder.binding.tvTicketStatus.text = "Belum Dibayar"
            holder.binding.tvTicketStatus.setTextColor(Color.RED)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}