package com.arifwidayana.challengechapter5.view.homepage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.arifwidayana.challengechapter5.R
import com.arifwidayana.challengechapter5.databinding.FragmentProfileUserBinding
import com.arifwidayana.challengechapter5.model.DatabaseStore
import com.arifwidayana.challengechapter5.model.utils.Constant
import com.arifwidayana.challengechapter5.model.utils.SharedHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileUserFragment : Fragment() {
    private var bind: FragmentProfileUserBinding? = null
    private val binding get() = bind!!
    private var user: DatabaseStore? = null
    private lateinit var shared: SharedHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentProfileUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shared = SharedHelper(requireContext())
        user = DatabaseStore.getData(requireContext())
        binding.apply {
            ivBackHome.setOnClickListener {
                findNavController().navigate(R.id.action_profileUserFragment_to_mainHomepageFragment)
            }

            fetchDataUser()
            btnEdit.setOnClickListener {
                findNavController().navigate(R.id.action_profileUserFragment_to_editProfileFragment)
                fetchDataUser()
            }

            btnLogout.setOnClickListener {
                shared.clear()
                Toast.makeText(requireContext(), "Logout Success", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_profileUserFragment_to_loginFragment)
            }
        }
    }

    private fun fetchDataUser() {
        binding.apply {
            CoroutineScope(Dispatchers.Main).launch {
                val getName = user?.userDao()?.getUsername(shared.getString(Constant.USERNAME).toString())
                tvGetUsername.text = getName?.username.toString()
                tvGetName.text = getName?.name.toString()
                tvGetEmail.text = getName?.email.toString()
                tvGetAge.text = getName?.age.toString()
                tvGetPhone.text = getName?.phone_number.toString()
            }
        }
    }

}