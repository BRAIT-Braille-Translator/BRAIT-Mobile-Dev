package com.bangkit.braitexample.data.response

import com.google.gson.annotations.SerializedName

data class PredictImageResponse(

	@field:SerializedName("data")
	val data: ResultPredict,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class ResultPredict(

	@field:SerializedName("text")
	val text: String
)
