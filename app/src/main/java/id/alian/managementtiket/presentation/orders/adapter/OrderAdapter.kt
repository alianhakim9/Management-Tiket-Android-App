package id.alian.managementtiket.presentation.orders.adapter

import android.annotation.SuppressLint
import android.graphics.Color.GREEN
import android.graphics.Color.RED
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.alian.managementtiket.commons.remove
import id.alian.managementtiket.commons.show
import id.alian.managementtiket.databinding.ItemOrderLayoutBinding
import id.alian.managementtiket.domain.model.Order

class OrderAdapter : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemOrderLayoutBinding) : RecyclerView.ViewHolder(binding.root)

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
            ItemOrderLayoutBinding.inflate(
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
        holder.binding.tvTicketPrice.text = "Price : $ ${orders.price}"
        holder.binding.tvTicketCount.text = "Count : ${orders.ticket_count}"

        if (orders.status != "0") {
            holder.binding.tvTicketStatus.text = "Sudah Dibayar"
            holder.binding.tvTicketStatus.setTextColor(GREEN)
            holder.binding.btnCheckout.remove()
            holder.binding.btnOrderDetail.show()
            holder.binding.btnOrderDetail.setOnClickListener {
                detailOrderListener?.let {
                    it(orders)
                }
            }
        } else {
            holder.binding.tvTicketStatus.text = "Belum Dibayar"
            holder.binding.tvTicketStatus.setTextColor(RED)
            holder.binding.btnCheckout.show()
            holder.binding.btnOrderDetail.remove()
            holder.binding.btnCheckout.setOnClickListener {
                onItemClickListener?.let {
                    it(orders)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Order) -> Unit)? = null
    private var detailOrderListener: ((Order) -> Unit)? = null

    fun setOnItemClickListener(listener: (Order) -> Unit) {
        onItemClickListener = listener
    }


    fun detailOrder(listener: (Order) -> Unit) {
        detailOrderListener = listener
    }
}