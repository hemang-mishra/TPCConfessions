package com.project.tpcconfessions

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitApi{

    @GET("/api/getConfession")
    suspend fun getConfessions(@Query("roll_no") roll_no: String = ""): Response<List<DataEntity>>

    @POST("/api/postConfession")
    suspend fun postConfessions(@Body bodyDataEntity: BodyDataEntity): Response<PostDataEntity>
}