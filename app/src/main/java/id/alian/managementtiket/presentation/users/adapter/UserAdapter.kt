package id.alian.managementtiket.presentation.users.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.alian.managementtiket.databinding.UserItemLayoutBinding
import id.alian.managementtiket.domain.model.User

class UserAdapter : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    class ViewHolder(val binding: UserItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    private val differCallback = object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            UserItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val users = differ.currentList[position]
        holder.binding.tvUserName.text = users.name
        holder.binding.tvUserEmail.text = users.email
        holder.binding.tvUserId.text = users.id.toString()
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}