package com.example.birthdayobg.repository

import com.example.birthdayobg.models.Person
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.PUT
import retrofit2.http.Query


interface PersonService {

   // @GET("Persons")
    // fun getAllPersons(): Call<List<Person>>

    @GET("Persons")
    fun getPersonByUserId(@Query("user_id") userId: String): Call<List<Person>>

    @POST("Persons")
    fun addPerson(@Body person: Person): Call<Person>

    @DELETE("Persons/{id}")
    fun deletePerson(@Path("id") id: Int): Call<Person>

    @PUT("Persons/{id}")
    fun updatePerson(@Path("id") id: Int, @Body person: Person): Call<Person>

}
