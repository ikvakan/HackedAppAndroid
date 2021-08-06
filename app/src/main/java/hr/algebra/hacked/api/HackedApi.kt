package hr.algebra.hacked.api

import hr.algebra.hacked.api.HackedItem
import retrofit2.Call
import retrofit2.http.GET

const val API_URL = "https://haveibeenpwned.com/api/v2/";

interface HackedApi {
    @GET("breaches")
    fun fetchItems(): Call<List<HackedItem>>

}