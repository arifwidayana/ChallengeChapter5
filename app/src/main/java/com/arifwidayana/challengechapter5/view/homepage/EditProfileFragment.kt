package com.arifwidayana.challengechapter5.view.homepage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.arifwidayana.challengechapter5.R
import com.arifwidayana.challengechapter5.databinding.FragmentEditProfileBinding
import com.arifwidayana.challengechapter5.model.DatabaseStore
import com.arifwidayana.challengechapter5.model.data.UserEntity
import com.arifwidayana.challengechapter5.model.utils.Constant
import com.arifwidayana.challengechapter5.model.utils.SharedHelper
import kotlinx.coroutines.*

@DelicateCoroutinesApi
class EditProfileFragment : Fragment() {
    private var bind : FragmentEditProfileBinding? = null
    private val binding get() = bind!!
    private var user: DatabaseStore? = null
    private lateinit var shared: SharedHelper
    private lateinit var data: UserEntity


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

    private fun deleteDataProfile() {

    }

    private fun saveDataProfile() {
        binding.apply {
//            val data = UserEntity(
//                null,
//                etEditName.text.toString(),
//                etEditEmail.text.toString(),
//                etEditAge.text.toString().toInt(),
//                etEditPhoneNumber.text.toString(),
//                shared.getString(Constant.USERNAME),
//                shared.getString(Constant.PASSWORD)
//            )

            data.apply {
                name = etEditName.text.toString()
                email = etEditEmail.text.toString()
                age = etEditAge.text.toString().toInt()
                phone_number = etEditPhoneNumber.text.toString()
            }

            lifecycleScope.launch(Dispatchers.IO) {
                user?.userDao()?.updateProfileUser(data)
            }
            Toast.makeText(requireContext(), "Edit Profile Success", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_editProfileFragment_to_profileUserFragment)
        }
    }

    private fun getDataProfile() {
        binding.apply {
            CoroutineScope(Dispatchers.Main).launch {
                val getData = user?.userDao()?.getUsername(shared.getString(Constant.USERNAME).toString())
                etEditName.setText(getData?.name.toString())
                etEditEmail.setText(getData?.email.toString())
                etEditAge.setText(getData?.age.toString())
                etEditPhoneNumber.setText(getData?.phone_number.toString())
            }
        }
    }
}