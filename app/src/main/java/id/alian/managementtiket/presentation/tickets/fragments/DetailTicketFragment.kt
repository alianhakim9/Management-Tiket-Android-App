package id.alian.managementtiket.presentation.tickets.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import id.alian.managementtiket.databinding.FragmentDetailTicketBinding

class DetailTicketFragment : Fragment() {

    private var _binding: FragmentDetailTicketBinding? = null
    private val binding get() = _binding!!
    private val args: DetailTicketFragmentArgs by navArgs()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailTicketBinding.inflate(layoutInflater)

        Log.d("DetailFragment", "onCreateView: ${args.ticketDetail}")
        requireActivity().runOnUiThread {
            binding.tvTicketTo.text = "To : ${args.ticketDetail?.to}"
            binding.tvTicketFrom.text = "From : ${args.ticketDetail?.from}"
            binding.tvTicketTime.text = "Time : ${args.ticketDetail?.time}"
            binding.tvTicketPrice.text = "Price : $ ${args.ticketDetail?.price}"
            binding.tvTicketStock.text = "Stock : ${args.ticketDetail?.ticket_stock}"
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}