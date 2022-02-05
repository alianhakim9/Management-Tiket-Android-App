package id.alian.managementtiket.presentation.users.fragments

import androidx.navigation.fragment.findNavController
import id.alian.managementtiket.R
import id.alian.managementtiket.presentation.BaseFragment
import id.alian.managementtiket.databinding.FragmentUserDashboardBinding

class UserDashboardFragment :
    BaseFragment<FragmentUserDashboardBinding>(FragmentUserDashboardBinding::inflate) {

    override fun FragmentUserDashboardBinding.initialize() {
        requireActivity().runOnUiThread {
            binding.cvProfileUser.setOnClickListener {
                findNavController().navigate(R.id.action_userDashboardFragment_to_userProfileFragment)
            }
        }
    }
}