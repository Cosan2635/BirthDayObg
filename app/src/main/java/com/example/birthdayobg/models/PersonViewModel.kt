package com.example.birthdayobg.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.birthdayobg.repository.PersonRepository
import com.google.firebase.auth.FirebaseAuth

class PersonViewModel : ViewModel() {
    private val repository = PersonRepository()
    val personsLiveData: LiveData<List<Person>> = repository.personsLiveData
    var errorMessageLiveData: LiveData<String> = repository.errorMessageLiveData
    val updateMessageLiveData: LiveData<String> = repository.updateMessageLiveData

    init {
        val email = FirebaseAuth.getInstance().currentUser?.email
        email?.let {
            reload(it)
        }
    }

    fun reload(userId: String) {
        Log.d("APPLE", "Reloading data for user: $userId")
        repository.getPersonById(userId)
    }

    operator fun get(index: Int): Person? {
        return personsLiveData.value?.get(index)
    }
    fun getPersonByUserId(userId: String)
    {
        repository.getPersonById(userId)
    }

    fun add(person: Person) {
        repository.add(person)
    }

    fun delete(id: Int) {
        repository.delete(id)
    }

    fun update(person: Person) {
        repository.update(person)
        Log.d("APPLE", "Updated person: $person" )
    }

    fun sortByName() {
        repository.sortByName()
    }

    fun sortByAge() {
        repository.sortByAge()
    }

    fun filterByName(name : String){
        repository.filterByName(name)
    }
    private var userEmail: String = ""

    fun getUserEmail(): String {
        return userEmail
    }
    fun setUserEmail(email: String){
        userEmail = email
    }

}
