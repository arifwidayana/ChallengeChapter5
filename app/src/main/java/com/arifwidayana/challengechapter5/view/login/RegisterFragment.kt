package com.arifwidayana.challengechapter5.view.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.arifwidayana.challengechapter5.R
import com.arifwidayana.challengechapter5.databinding.FragmentRegisterBinding
import com.arifwidayana.challengechapter5.model.DatabaseStore
import com.arifwidayana.challengechapter5.model.data.UserEntity
import kotlinx.coroutines.*

@DelicateCoroutinesApi
class RegisterFragment : Fragment() {
    private var bind : FragmentRegisterBinding? = null
    private val binding get() = bind!!
    private var dataUser: DatabaseStore? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataUser = DatabaseStore.getData(requireContext())

        binding.apply {
            tvSignIn.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }

            btnRegister.setOnClickListener {
                when {
                    etRegisterName.text.isNullOrEmpty() -> etRegisterName.error = "Fill column name"
                    etRegisterEmail.text.isNullOrEmpty() -> etRegisterEmail.error = "Fill column email"
                    etRegisterAge.text.isNullOrEmpty() -> etRegisterAge.error = "Fill column age"
                    etRegisterPhoneNumber.text.isNullOrEmpty() -> etRegisterPhoneNumber.error = "Fill column phone number"
                    etRegisterUsername.text.isNullOrEmpty() -> etRegisterUsername.error = "Fill column username"
                    etRegisterPassword.text.isNullOrEmpty() -> etRegisterPassword.error = "Fill column password"
                    else -> {
                        val objDataUser = UserEntity(
                            null,
                            etRegisterName.text.toString(),
                            etRegisterEmail.text.toString(),
                            etRegisterAge.text.toString().toInt(),
                            etRegisterPhoneNumber.text.toString(),
                            etRegisterUsername.text.toString(),
                            etRegisterPassword.text.toString()
                        )

                        GlobalScope.launch {
                            dataUser?.userDao()?.insertUser(objDataUser)
                        }

                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(
                                requireContext(),
                                "Register User Success",
                                Toast.LENGTH_SHORT
                            ).show()
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        }
                    }
                }
            }
        }
    }
}