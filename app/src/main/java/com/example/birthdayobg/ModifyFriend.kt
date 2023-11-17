package com.example.birthdayobg

import android.app.DatePickerDialog
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.birthdayobg.databinding.FragmentModifyFriendBinding
import com.example.birthdayobg.models.Person
import com.example.birthdayobg.models.PersonViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import java.util.Calendar


class ModifyFriend : Fragment() {
    private var _binding: FragmentModifyFriendBinding? = null
    private val binding get() = _binding!!
    private val personViewModel: PersonViewModel by activityViewModels()
    private var selectedDate = Calendar.getInstance()
    private val dateSetListener =
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            selectedDate = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentModifyFriendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = requireArguments()
        val modifyFriendArgs: ModifyFriendArgs = ModifyFriendArgs.fromBundle(bundle)
        val position = modifyFriendArgs.position
        val person = personViewModel[position]
        if (person == null) {
            binding.textviewMessage.text = "No Such friend!"
        }
        binding.edittextName.setText(person?.name)
        binding.buttonBirthday.setOnClickListener{
            val calendar = Calendar.getInstance()
            val currentYear = calendar[Calendar.YEAR]
            val currentMonth = calendar[Calendar.MONTH]
            val currentdayOfMonth = calendar[Calendar.DAY_OF_MONTH]
            val datePicker = DatePickerDialog(
                requireContext(),
                dateSetListener,
                currentYear,
                currentMonth,
                currentdayOfMonth
            )
            datePicker.show()
        }
        binding.buttonDelete.setOnClickListener {
            val user_id = FirebaseAuth.getInstance().currentUser?.email
            if (user_id != null) {
                personViewModel.delete(person!!.id)
                personViewModel.reload(user_id)
            }
            findNavController().navigate(R.id.action_modifyFriend_to_SecondFragment)
        }
        binding.buttonCancel.setOnClickListener {
            findNavController().navigate(R.id.action_modifyFriend_to_SecondFragment)
        }

        binding.buttonSave.setOnClickListener {
            val name = binding.edittextName.text.toString().trim()
            val birthYear = selectedDate?.get(Calendar.YEAR)?:0
            val birthMonth = selectedDate?.get(Calendar.MONTH)?:0
            val birthDayOfMonth = selectedDate?.get(Calendar.DAY_OF_MONTH)?:0
            val Email = person!!.userId
            if (name.isEmpty()) {
                binding.edittextName.error = "Name field is Empty"
            } else {
                val updatePerson =
                    Person(
                    person!!.id,
                    name,
                    person.age,
                    birthYear,
                    birthMonth,
                    birthDayOfMonth,
                    Email
                )
                personViewModel.update(updatePerson)
                val user_id = FirebaseAuth.getInstance().currentUser?.email
                if (user_id != null) {
                    //personViewModel.update(person)
                    personViewModel.reload(user_id)
                }
                findNavController().navigate(R.id.action_modifyFriend_to_SecondFragment)
            }
        }
    }
    override fun onDestroyView() {
      super.onDestroyView()
      _binding = null
    }
}
