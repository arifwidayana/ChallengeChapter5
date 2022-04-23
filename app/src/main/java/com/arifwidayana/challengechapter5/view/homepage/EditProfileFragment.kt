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
import com.arifwidayana.challengechapter5.databinding.FragmentEditProfileBinding
import com.arifwidayana.challengechapter5.model.DatabaseStore
import com.arifwidayana.challengechapter5.model.data.UserEntity
import com.arifwidayana.challengechapter5.model.utils.Constant
import com.arifwidayana.challengechapter5.model.utils.SharedHelper
import com.arifwidayana.challengechapter5.viewmodel.UserViewModel
import kotlinx.coroutines.*

class EditProfileFragment : Fragment() {
    private var bind : FragmentEditProfileBinding? = null
    private val binding get() = bind!!
    private var user: DatabaseStore? = null
    private lateinit var shared: SharedHelper
    private lateinit var userViewModel: UserViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shared = SharedHelper(requireContext())
        user = DatabaseStore.getData(requireContext())
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        binding.apply {
            getDataProfile()
            ivBackProfile.setOnClickListener {
                findNavController().navigate(R.id.action_editProfileFragment_to_profileUserFragment)
            }

            btnSave.setOnClickListener {
                saveDataProfile()
            }

            btnDelete.setOnClickListener {
                deleteDataProfile()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bind = null
    }

    private fun getDataProfile() {
        val username = shared.getString(Constant.USERNAME)
        when{
            user != null -> getUser(username)
        }
        binding.apply {
            userViewModel.user.observe(viewLifecycleOwner){
                etEditName.setText(it.name)
                etEditEmail.setText(it.email)
                etEditAge.setText(it.age.toString())
                etEditPhoneNumber.setText(it.phone_number)
            }
        }
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

    private fun saveDataProfile() {
        binding.apply {
            when {
                etEditName.text.isNullOrEmpty() -> etEditName.error = "Fill the name"
                etEditEmail.text.isNullOrEmpty() -> etEditEmail.error = "Fill the name"
                etEditAge.text.isNullOrEmpty() -> etEditAge.error = "Fill the age"
                etEditPhoneNumber.text.isNullOrEmpty() -> etEditPhoneNumber.error = "Fill the phone number"
                else -> {
                    userViewModel.user.observe(viewLifecycleOwner){
                        val newData = UserEntity(
                            it.id,
                            etEditName.text.toString(),
                            etEditEmail.text.toString(),
                            etEditAge.text.toString().toInt(),
                            etEditPhoneNumber.text.toString(),
                            it.username,
                            it.password
                        )

                        lifecycleScope.launch(Dispatchers.IO) {
                            val res = user?.userDao()?.updateProfileUser(newData)
                            runBlocking(Dispatchers.Main) {
                                when {
                                    res != 0 -> {
                                        Toast.makeText(requireContext(), "Edit Profile Success", Toast.LENGTH_SHORT).show()
                                        findNavController().navigate(R.id.action_editProfileFragment_to_profileUserFragment)
                                    }
                                    else -> Toast.makeText(requireContext(), "Edit Profile Failed", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun deleteDataProfile() {
        binding.apply {
            userViewModel.user.observe(viewLifecycleOwner){
                val newData = UserEntity(
                    it.id,
                    etEditName.text.toString(),
                    etEditEmail.text.toString(),
                    etEditAge.text.toString().toInt(),
                    etEditPhoneNumber.text.toString(),
                    it.username,
                    it.password
                )

                lifecycleScope.launch(Dispatchers.IO) {
                    val res = user?.userDao()?.deleteUser(newData)
                    runBlocking(Dispatchers.Main) {
                        when {
                            res != 0 -> {
                                shared.clear()
                                Toast.makeText(requireContext(), "Delete User Success", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_editProfileFragment_to_loginFragment)
                            }
                            else -> Toast.makeText(requireContext(), "Delete User Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}