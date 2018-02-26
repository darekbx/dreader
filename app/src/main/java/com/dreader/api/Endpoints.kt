package com.dreader.api

import com.dreader.model.Wrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Endpoints {

    @Headers("User-Agent: androidFuturemind")
    @GET("api/v0/items?status=main&offset=0&limit=50&sort=accepted_at&order=desc")
    fun getItems(@Query("offset") offset: Int): Call<Wrapper>


}