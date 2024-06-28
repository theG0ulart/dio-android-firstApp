package edu.wearedev.firstapp.data

import edu.wearedev.firstapp.domain.Carro
import retrofit2.http.GET

interface CarsApi {

    @GET("cars.json")
    fun getAllCars() : retrofit2.Call<List<Carro>>

}