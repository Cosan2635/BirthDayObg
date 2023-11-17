package com.example.birthdayobg

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.birthdayobg.databinding.FragmentFirstBinding
import com.example.birthdayobg.models.PersonViewModel
import com.google.firebase.auth.FirebaseAuth

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private  val personViewModel: PersonViewModel by activityViewModels()

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentUser = auth.currentUser
        if (currentUser != null) {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        binding.btnLogin.setOnClickListener {
            val email = binding.emailEditText.text?.trim().toString()
            if (email.isEmpty()) {
                binding.emailEditText.error = "No Email"
                return@setOnClickListener
            }
            val password = binding.passwordEditText.text?.trim().toString()
            if (password.isEmpty()) {
                binding.passwordEditText.error = "No password"
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Log.d("APPLE", "createUserWithEmail:succes")
                        val user = auth.currentUser
                        val userEmail = user?.email
                        personViewModel.setUserEmail(userEmail ?: "")
                        personViewModel.getPersonByUserId(email)
                        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                    } else {
                        Log.w("APPLE", "createUserWithEmail:failure", task.exception)
                        binding.textviewMessage.text =
                            "Login mislykkedes. :" + task.exception?.message
                    }
                }
        }
        binding.btnRegister.setOnClickListener {
            val email = binding.emailEditText.text?.trim().toString()
            if (email.isEmpty()) {
                binding.emailEditText.error = "No email"
                return@setOnClickListener
            }
            val password = binding.passwordEditText.text?.trim().toString()
            if (password.isEmpty()) {
                binding.passwordEditText.error = "No Password"
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Log.d("APPLE", "createUserWithEmail:success")
                        val user = auth.currentUser
                        personViewModel.getPersonByUserId(email)
                        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                    } else {
                        Log.w("APPLE", "createUserWithEmail:failure", task.exception)
                        binding.textviewMessage.text =
                            "Registration failed: " + task.exception?.message
                    }
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
