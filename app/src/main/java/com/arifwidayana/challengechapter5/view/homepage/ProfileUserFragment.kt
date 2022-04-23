package com.arifwidayana.challengechapter5.view.homepage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.arifwidayana.challengechapter5.R
import com.arifwidayana.challengechapter5.databinding.FragmentProfileUserBinding
import com.arifwidayana.challengechapter5.model.DatabaseStore
import com.arifwidayana.challengechapter5.model.utils.Constant
import com.arifwidayana.challengechapter5.model.utils.SharedHelper
import com.arifwidayana.challengechapter5.viewmodel.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProfileUserFragment : Fragment() {
    private var bind: FragmentProfileUserBinding? = null
    private val binding get() = bind!!
    private var user: DatabaseStore? = null
    private lateinit var shared: SharedHelper
    private lateinit var userViewModel: UserViewModel

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
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        binding.apply {
            val username = shared.getString(Constant.USERNAME)
            when{
                user != null -> getUser(username)
            }

            fetchDataUser()

            ivBackHome.setOnClickListener {
                findNavController().navigate(R.id.action_profileUserFragment_to_mainHomepageFragment)
            }

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

    override fun onDestroyView() {
        super.onDestroyView()
        bind = null
    }

    private fun getUser(username: String?) {
        lifecycleScope.launch(Dispatchers.IO) {
            val data = user?.userDao()?.getUsername(username)
            runBlocking(Dispatchers.Main) {
                data?.let {
                    userViewModel.dataUser(it)
                }
            }
        }
    }

    private fun fetchDataUser() {
        binding.apply {
            userViewModel.user.observe(viewLifecycleOwner){
                tvGetUsername.text = it.username
                tvGetName.text = it.name
                tvGetEmail.text = it.email
                tvGetAge.text = it.age.toString()
                tvGetPhone.text = it.phone_number
            }
        }
    }
}