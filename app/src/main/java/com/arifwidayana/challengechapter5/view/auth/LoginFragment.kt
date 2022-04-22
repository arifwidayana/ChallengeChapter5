package com.arifwidayana.challengechapter5.view.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.arifwidayana.challengechapter5.R
import com.arifwidayana.challengechapter5.databinding.FragmentLoginBinding
import com.arifwidayana.challengechapter5.model.DatabaseStore
import com.arifwidayana.challengechapter5.model.utils.Constant
import com.arifwidayana.challengechapter5.model.utils.SharedHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private var bind : FragmentLoginBinding? = null
    private val binding get() = bind!!
    private var dataUser: DatabaseStore? = null
    private lateinit var shared: SharedHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataUser = DatabaseStore.getData(requireContext())
        shared = SharedHelper(requireContext())

        binding.apply {
            tvRegister.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }

            btnLogin.setOnClickListener {
                binding.apply {
                    val user = etUsername.text.toString()
                    val pass = etPassword.text.toString()

                    when {
                        user.isEmpty() && pass.isEmpty() -> {
                            tfUsername.error = "Fill the username"
                            tfPassword.error = "Fill the password"
                        }
                        user.isEmpty() -> tfUsername.error = "Fill the username"
                        pass.isEmpty() -> tfPassword.error = "Fill the password"
                        else -> {
                            CoroutineScope(Dispatchers.Main).launch {
                                val data = dataUser?.userDao()?.getUsername(user)
                                when (data?.username) {
                                    user -> when (data.password) {
                                        pass -> {
                                            loginSession(user, pass)
                                            Toast.makeText(requireContext(), "Login Success", Toast.LENGTH_SHORT).show()
                                            findNavController().navigate(R.id.action_loginFragment_to_mainHomepageFragment)
                                        }
                                        else -> Toast.makeText(requireContext(), "Wrong Password", Toast.LENGTH_SHORT).show()
                                    }
                                    else -> Toast.makeText(requireContext(), "Wrong Username", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (shared.getBoolean(Constant.LOGIN, false)){
            findNavController().navigate(R.id.action_loginFragment_to_mainHomepageFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bind = null
    }

    private fun loginSession(user: String, pass: String) {
        shared.apply {
            put(Constant.USERNAME, user)
            put(Constant.PASSWORD, pass)
            put(Constant.LOGIN, true)
        }
    }
}