package com.example.birthdayobg

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.birthdayobg.databinding.FragmentAddFriendBinding
import com.example.birthdayobg.models.Person
import com.example.birthdayobg.models.PersonViewModel
import com.google.firebase.auth.FirebaseAuth
import java.util.Calendar


class AddFriend : Fragment() {
private var _binding : FragmentAddFriendBinding? = null
    private val binding get() = _binding!!
private val personViewModel : PersonViewModel by activityViewModels()
    private var selectedDate = Calendar.getInstance()
    val auth = FirebaseAuth.getInstance()
    private val dataSetListener =
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            selectedDate = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            arguments?.let {

            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddFriendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nameEditText = binding.edittextName
       // val AgeEditText = binding.edittextAge

        binding.buttonBirthday.setOnClickListener {
            val calender = Calendar.getInstance()
            val currentYear = calender [Calendar.YEAR]
            val currentMonth = calender [Calendar.MONTH]
            val currentDayOfMonth = calender[Calendar.DAY_OF_MONTH]

            val datePicker = DatePickerDialog(
                requireContext(),
                dataSetListener,
                currentYear,
                currentMonth,
                currentDayOfMonth
            )
            datePicker.show()
        }
        val user_id = auth.currentUser
        binding.buttonAdd.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val today = Calendar.getInstance()
            val age = today.get(Calendar.YEAR)
            if (today.get(Calendar.DAY_OF_YEAR) < (selectedDate?.get(Calendar.DAY_OF_YEAR))
                ?: 0
            ) 1 else 0

            val userEmail = personViewModel.getUserEmail()
            val birthYear = selectedDate?.get(Calendar.YEAR) ?: 0
            val birthMonth = selectedDate?.get(Calendar.MONTH)?: 0
            val birthDayOfMonth = selectedDate?.get(Calendar.DAY_OF_MONTH)?: 0
            val addFriend = Person(name, age, birthYear, birthMonth, birthDayOfMonth, userEmail )

            personViewModel.add(addFriend)
            findNavController().navigate(R.id.action_addFriend_to_SecondFragment)
        }
        binding.buttonCancel.setOnClickListener {
            findNavController().navigate(R.id.action_addFriend_to_SecondFragment)
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
