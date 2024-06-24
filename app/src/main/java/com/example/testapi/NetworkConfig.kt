package com.example.testapi

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

object NetworkConfig {
    fun getInterceptor(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    // Retrofit configuration
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.18.128/server_com/index.php/ServerCom/")  // Ensure this is the correct base URL
            .client(getInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getService(): ComicService = getRetrofit().create(ComicService::class.java)
}

interface ComicService {
    @FormUrlEncoded
    @POST("addComic")
    fun addComic(@Field("title") title: String,
                 @Field("author") author: String,
                 @Field("image") image: String,
                 @Field("description") description: String): Call<ResultStatus>

    @GET("getDataComic")
    fun getData(): Call<ResultComic>

    @FormUrlEncoded
    @POST("updateComic")
    fun updateComic(@Field("id") id: String,
                    @Field("title") title: String,
                    @Field("author") author: String,
                    @Field("image") image: String,
                    @Field("description") description: String): Call<ResultStatus>

    @FormUrlEncoded
    @POST("deleteComic")
    fun deleteComic(@Field("id") id: String): Call<ResultStatus>
}