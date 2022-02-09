package id.alian.managementtiket.presentation.orders.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color.GREEN
import android.graphics.Color.RED
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.alian.managementtiket.R
import id.alian.managementtiket.commons.remove
import id.alian.managementtiket.commons.show
import id.alian.managementtiket.databinding.ItemOrderLayoutBinding
import id.alian.managementtiket.domain.model.Order
import javax.inject.Inject

class OrderAdapter @Inject constructor(
    val context: Context
) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {
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
        with(holder.binding) {
            tvTicketFrom.text = "Dari : ${orders.ticket.from}"
            tvTicketTo.text = "Tujuan : ${orders.ticket.to}"
            tvTicketTime.text = "Waktu Keberangkatan : ${orders.ticket.time}"
            tvTicketPrice.text = "Harga Tiket : Rp ${orders.price}"
            tvTicketCount.text = "Jumlah Tiket : ${orders.ticket_count}"

            if (orders.status != "0") {
                tvTicketStatus.text = "Sudah Dibayar"
                tvTicketStatus.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.primary900
                    )
                )
                btnCheckout.remove()
                btnOrderDetail.show()
                btnOrderDetail.setOnClickListener {
                    detailOrderListener?.let {
                        it(orders)
                    }
                }
            } else {
                tvTicketStatus.text = "Belum Dibayar"
                tvTicketStatus.setTextColor(RED)
                btnCheckout.show()
                btnOrderDetail.remove()
                btnCheckout.setOnClickListener {
                    onItemClickListener?.let {
                        it(orders)
                    }
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