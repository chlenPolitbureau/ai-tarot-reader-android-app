package com.tarotreader.app.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TarotReaderAPIService {

    @POST("predict")
    suspend fun getPrediction(
//        @Body q: String,
        @Body PredictRequest: PredictRequest
    ): Response<PredictionResponse>
}

data class PredictRequest(
    val q: String,
    val cardsPicked: String? = null,
    val spread: String? = null
    )

data class PredictionResponse(
    val prediction: String,
    val prediction_id: String,
)