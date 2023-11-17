package com.example.birthdayobg

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.birthdayobg.databinding.FragmentSecondBinding
import com.example.birthdayobg.models.MyAdapter
import com.example.birthdayobg.models.PersonViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val personViewModel: PersonViewModel by activityViewModels()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        personViewModel.personsLiveData.observe(viewLifecycleOwner) { persons ->
            Log.d("APPLE", persons.toString())

            binding.progressbar.visibility = View.GONE
            binding.recyclerView.visibility = if (persons == null) View.GONE else View.VISIBLE

            if (persons != null) {
                val adapter = MyAdapter(persons) { position ->
                    val action =
                        SecondFragmentDirections.actionSecondFragmentToModifyFriend(position)
                    findNavController().navigate(action)
                }

                var columns = 2
                val currentOrientation = this.resources.configuration.orientation
                if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    columns = 4

                } else if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
                    columns = 2
                }
                binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
                binding.recyclerView.adapter = adapter
            }
        }
        personViewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            binding.textviewMessage.text = errorMessage
        }



        binding.swiperefresh.setOnRefreshListener {
            var email = FirebaseAuth.getInstance().currentUser?.email
            if(email == null){
                binding.textviewMessage.text = ""
             //   binding.swiperefresh.isRefreshing = true
            }else
            personViewModel.reload(email)
            binding.swiperefresh.isRefreshing = false
        }


        binding.buttonSort.setOnClickListener {
            when (binding.spinnerPersons.selectedItemPosition) {
                0 -> personViewModel.sortByName()
                1 -> personViewModel.sortByAge()
            }
        }
        binding.buttonFilter.setOnClickListener {
            val filter = binding.edittextFilterName.text.toString().trim()
            if (filter.isBlank()) {
                binding.edittextFilterName.error = " No Title"
                return@setOnClickListener
            }
            personViewModel.filterByName(filter)

        }
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            findNavController().navigate(R.id.action_SecondFragment_to_addFriend)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}