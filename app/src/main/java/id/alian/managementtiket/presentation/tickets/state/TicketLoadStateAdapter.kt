package id.alian.managementtiket.presentation.tickets.state

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import id.alian.managementtiket.commons.show
import id.alian.managementtiket.databinding.ItemLoadingStateBinding

class TicketLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<TicketLoadStateAdapter.TicketLoadStateViewHolder>() {

    inner class TicketLoadStateViewHolder(
        private val binding: ItemLoadingStateBinding,
        private val retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.textViewError.text = loadState.error.localizedMessage
            }

            binding.progressbar.isVisible = loadState is LoadState.Loading
            binding.buttonRetry.isVisible = loadState is LoadState.Error
            binding.textViewError.isVisible = loadState is LoadState.Error
            binding.textViewError.setOnClickListener {
                retry()
            }

            binding.progressbar.show()
        }
    }

    override fun onBindViewHolder(
        holder: TicketLoadStateAdapter.TicketLoadStateViewHolder,
        loadState: LoadState
    ) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = TicketLoadStateViewHolder(
        ItemLoadingStateBinding.inflate(LayoutInflater.from(parent.context), parent, false), retry
    )

}