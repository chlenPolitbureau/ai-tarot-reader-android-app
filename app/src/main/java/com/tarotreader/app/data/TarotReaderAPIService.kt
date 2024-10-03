package com.tarotreader.app.data

import com.tarotreader.app.model.Prediction
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TarotReaderAPIService {

    @POST("predict")
    suspend fun getPrediction(@Body q: PredictRequest): Response<PredictionResponse>
}

data class PredictRequest(val q: String)

data class PredictionResponse(val prediction: String, val prediction_id: String)